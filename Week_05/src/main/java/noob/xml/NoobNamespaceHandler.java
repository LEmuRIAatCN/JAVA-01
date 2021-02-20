package noob.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NoobNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("student", new NoobBeanDefinitionParser());
        registerBeanDefinitionParser("klass", new NoobBeanDefinitionParser());
    }
}
