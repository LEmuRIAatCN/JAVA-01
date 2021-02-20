package noob.beans;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class NeedBean1 {
    @Autowired(required = true)
    private Bean1 bean1;
    public NeedBean1(){
        System.out.println("need bean1");
        System.out.println("bean1: "+bean1);
    }
    @PostConstruct
    public void init(){
        System.out.println("bean1: "+bean1);
    }

    public Bean1 getBean1() {
        return bean1;
    }

    public void setBean1(Bean1 bean1) {
        this.bean1 = bean1;
    }
}
