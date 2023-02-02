package be.technifutur.java.timairport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.httpBasic();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /**
         * Les premiers matchers ont la priorité
         * anyRequest, s'il est mis, doit être le dernier matcher
         *
         * RequestMatchers:
         *
         *      Lambda RequestMatchers:
         *      - prend une HttpServletRequest en param, renvoi un boolean
         *
         *      Method:
         *      - une valeur de l'enum HttpMethod
         *
         *      Pattern: 1 ou pls chaine de carac. représentant des URIs
         *      - ? : remplace une lettre
         *      - * : n'importe quelles valeurs dans 1 segment
         *      - {machin:regex} : n'imp. quelles valeurs correspondant au pattern regex pour 1 segment
         *      - ** : n'importe quelle valeur dans 0 à N segments (seulement en dernier segment)
         *
         * Authorization:
         *      - permitAll():          tout le monde passe
         *      - denyAll():            personne ne passe
         *      - authenticated():      les users authentifiés
         *      - anonymous():          les users non authentifiés
         *      - hasAuthority(?)
         *      - hasAnyAuthority(...?)
         *      - hasRole(?)
         *      - hasAnyRole(...?)
         *
         *      Une Authority c'est un droit sous forme de String (plus un droit à une action)
         *      Un Role est une Authority qui commence par 'ROLE_' (qui est l'utilisateur pour mon app)
         *
         *      auth: 'ROLE_TRUC' -> role: 'TRUC'
         *      auth: 'MACHIN' -> (/) role
         */


        http.authorizeHttpRequests( (authorize) -> {
            authorize
                    // via lambda RequestMatchers
                    .requestMatchers( request -> request.getRequestURI().length() > 50 ).hasRole("ADMIN")
                    // via HttpMethod
                    .requestMatchers( HttpMethod.POST ).hasRole("ADMIN")
                    // via mapping d'URI
                    .requestMatchers("/plane/all").anonymous()
                    .requestMatchers("/plane/add").authenticated()
                    .requestMatchers("/plane/{id:[0-9]+}/?pdate").hasRole("ADMIN")//.hasAuthority("ROLE_ADMIN")
                    // via HttpMethod + mapping d'URI
                    .requestMatchers(HttpMethod.GET, "/plane/*")
                                    .hasAnyRole("USER", "ADMIN")
                                    // .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .anyRequest().permitAll();
        });

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService( PasswordEncoder encoder ){

        List<UserDetails> users = List.of(
                User.builder()
                        .username("user")
                        .password( encoder.encode("pass") )
                        .roles("USER")
                        .build(),
                User.builder()
                        .username("admin")
                        .password( encoder.encode("pass") )
                        .roles("ADMIN", "USER")
                        .build()
        );

        return new InMemoryUserDetailsManager( users );

    }

}
