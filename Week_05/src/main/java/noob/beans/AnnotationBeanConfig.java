package noob.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationBeanConfig {
    @Bean(name = "annoBean1")
    public AnnotationBean1 getAnnoBean1(){
        return new AnnotationBean1();
    }
    @Bean(name = "annoBean2")
    public AnnotationBean2 getAnnoBean2(){
        return new AnnotationBean2();
    }
}
