package be.technifutur.java.timairport.config;

import be.technifutur.java.timairport.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//
//        Map<String,PasswordEncoder> encoders= new HashMap<>();
//        encoders.put("bcrypt",new BCryptPasswordEncoder());
//        encoders.put("scrypt",SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
//
//        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder("bcrypt",encoders);
//        encoder.setDefaultPasswordEncoderForMatches(encoders.get("bcrypt"));
//        return encoder;
//    }

    /**
     * Les premiers matchers ont la priorité
     * anyRequest, s'il est mis, doit être le dernier matcher
     *
     * <h2>RequestMatchers:</h2>
     *
     *      <h3>Lambda RequestMatchers:</h3>
     *      <ul>
     *          <li>prend une HttpServletRequest en param, renvoi un boolean</li>
     *      </ul>
     *
     *      <h3>Method:</h3>
     *      <ul>
     *          <li>une valeur de l'enum HttpMethod</li>
     *      </ul>
     *
     *      <h3>Pattern: 1 ou pls chaine de carac. représentant des URIs</h3>
     *      <ul>
     *          <li>? : remplace une lettre</li>
     *          <li>* : n'importe quelles valeurs dans 1 segment</li>
     *          <li>{machin:regex} : n'imp. quelles valeurs correspondant au pattern regex pour 1 segment</li>
     *          <li>** : n'importe quelle valeur dans 0 à N segments (seulement en dernier segment)</li>
     *      </ul>
     *
     * <h2>Authorization:</h2>
     * <ul>
     *     <li>permitAll():          tout le monde passe</li>
     *     <li>denyAll():            personne ne passe</li>
     *     <li>authenticated():      les users authentifiés</li>
     *     <li>anonymous():          les users non authentifiés</li>
     *     <li>hasAuthority(?)</li>
     *     <li>hasAnyAuthority(...?)</li>
     *     <li>hasRole(?)</li>
     *     <li>hasAnyRole(...?)</li>
     * </ul>
     *
     * <p>
     *     Une Authority c'est un droit sous forme de String (plus un droit à une action)<br/>
     *     Un Role est une Authority qui commence par 'ROLE_' (qui est l'utilisateur pour mon app)
     * </p>
     *
     * <dl>
     *     <dt>auth: 'ROLE_TRUC' -></dt>
     *     <dd>role: 'TRUC'</dd>
     *     <dt>auth: 'MACHIN' -></dt>
     *     <dd>(/) role</dd>
     * </dl>
     *
     * @param http the builder for the SecurityFilterChain
     * @return a configured SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {

        http.csrf().disable();

        http.httpBasic().disable();
        http.addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class );
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests( (authorize) -> {
            authorize
                    .requestMatchers(HttpMethod.POST, "/auth/*").anonymous()
                    // via lambda RequestMatchers
                    .requestMatchers( request -> request.getRequestURI().length() > 50 ).hasRole("ADMIN")
                    // via mapping d'URI
                    .requestMatchers("/plane/all").anonymous()
                    .requestMatchers("/plane/add").authenticated()
                    .requestMatchers("/plane/{id:[0-9]+}/?pdate").hasRole("ADMIN")//.hasAuthority("ROLE_ADMIN")
                    // via HttpMethod
                    .requestMatchers( HttpMethod.POST ).hasRole("ADMIN")
                    // via HttpMethod + mapping d'URI
//                    .requestMatchers(HttpMethod.GET, "/plane/*")
//                                    .hasAnyRole("USER","ADMIN")
                                    // .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .anyRequest().permitAll();
        });

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService( PasswordEncoder encoder ){
//
//        List<UserDetails> users = List.of(
//                User.builder()
//                        .username("user")
//                        .password( encoder.encode("pass") )
//                        .roles("USER")
//                        .build(),
//                User.builder()
//                        .username("admin")
//                        .password( encoder.encode("pass") )
//                        .roles("ADMIN", "USER")
//                        .build()
//        );
//
//        return new InMemoryUserDetailsManager( users );
//
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
