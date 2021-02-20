package noob.starter;

import lombok.RequiredArgsConstructor;
import noob.beans.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(NoobPropertiesConfiguration.class)
@ConditionalOnProperty(prefix = "noob.spring.boot", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class NoobSpringBootConfiguration implements EnvironmentAware {
    private final NoobPropertiesConfiguration noobPropertiesConfiguration;
    private final Map<String, Object> someProperties = new HashMap<>();
    @Bean
    public Student noobStudent(){
        return new Student();
    }
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(noobPropertiesConfiguration);
        System.out.println(someProperties);

    }
}
