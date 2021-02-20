package noob.beans;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class NeedBean2 {
    private Bean2 bean2;
    @Autowired
    private AnnotationBean2 annotationBean2;

    public NeedBean2(){
        System.out.println("need bean2");
    }
    @PostConstruct
    public void init(){
        System.out.println(bean2);
        System.out.println(annotationBean2);
    }

    public AnnotationBean2 getAnnotationBean2() {
        return annotationBean2;
    }

    public void setAnnotationBean2(AnnotationBean2 annotationBean2) {
        this.annotationBean2 = annotationBean2;
    }

    public Bean2 getBean2() {
        return bean2;
    }

    public void setBean2(Bean2 bean2) {
        this.bean2 = bean2;
    }
}
