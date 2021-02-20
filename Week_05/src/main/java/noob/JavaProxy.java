package noob;

import noob.impl.HighSchool;
import noob.school.School;

import java.lang.reflect.*;

public class JavaProxy {
    public static <T> T getProxy(Class<T> tClass) {
        Class<?> proxyClass = Proxy.getProxyClass(tClass.getClassLoader(), tClass);
        try {
            Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
            T instance = (T) constructor.newInstance(new SchoolInvocationHandler());
            return instance;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class SchoolInvocationHandler implements InvocationHandler {
        private School school = new HighSchool();
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("hi from proxy");
            return method.invoke(school, args);
        }
    }

    public static void main(String[] ar){
        School school = JavaProxy.getProxy(School.class);
        System.out.println(school.isCompulsory());
        System.out.println(school.years());
        System.out.println(school);
    }
}
