package be.technifutur.java.timairport.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "custom")
public class CustomProp implements InitializingBean {

    private String prop;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("\n--\n"+prop+"\n--\n");
    }
}
