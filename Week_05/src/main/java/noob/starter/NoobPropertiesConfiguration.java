package noob.starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(prefix = "noob.spring.boot")
@Getter
@Setter
public class NoobPropertiesConfiguration {
    private Properties props = new Properties();
}
