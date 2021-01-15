package noob;

import java.io.*;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {
    public static void main(String[] s) {
        try {
            newYlass();
            MyClassLoader myClassLoader = new MyClassLoader();
//            Class<MyClassToLoad> c = (Class<MyClassToLoad>) myClassLoader.findClass("noob.MyClassToLoad");
            Class<Hello> c = (Class<Hello>) myClassLoader.findClass("noob.Hello");
            Method method = c.getMethod("hello", null);
            //这里是执行hello()，打印一句话
            method.invoke(c.newInstance(), null);
            //如下会得到noob.Hello cannot be cast to noob.Hello
            //因为这里的两个Hello.class是通过不同的classloader加载进来的
            try {
                Hello hello = c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
                Hello是在main方法中通过AppClassLoader进行的加载
                而c.newInstance()所创建的实例的所属的Class是通过自定义的classloader加载的

                添加参数-verbose获得如下日志
                *******************************************************
                [Loaded java.io.FileNotFoundException from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]
                [Loaded java.lang.Void from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]

                //########这里通过自定义类加载器进行的装在########
                [Loaded noob.Hello from __JVM_DefineClass__]

                //########这里是作业的要求########
                Hello, classLoader!
                [Loaded noob.Hello from file:/E:/idea_projects/JAVA-01/out/production/JAVA-01/]
                [Loaded java.lang.Throwable$PrintStreamOrWriter from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]
                [Loaded java.lang.Throwable$WrappedPrintStream from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]
                [Loaded java.util.IdentityHashMap from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]
                [Loaded java.util.IdentityHashMap$KeySet from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]

                //########这里是main函数中Hello通过常规的classpath方式加载########
                sun.misc.Launcher$AppClassLoader@18b4aac2
                [Loaded java.lang.Shutdown from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]
                [Loaded java.lang.Shutdown$Lock from C:\Program Files\Java\jdk1.8.0_251\jre\lib\rt.jar]
                java.lang.ClassCastException: noob.Hello cannot be cast to noob.Hello
	                at noob.MyClassLoader.main(MyClassLoader.java:19)
                *******************************************************
             */
            System.out.println(Hello.class.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照xlass的方式，创建一个ylass
     * 需要路径下存在Hello.class
     */
    public static void newYlass() {
        File classFile = new File(System.getProperty("user.dir") + "\\Week_01\\src\\main\\java\\noob\\Hello.class");
        try {
            FileInputStream fileInputStream = new FileInputStream(classFile);
            int length = fileInputStream.available();
            byte[] bytes = new byte[length];
            fileInputStream.read(bytes);
            for (int i = 0; i < length; i++) {
                bytes[i] = (byte) (bytes[i] + 1);
            }
            File newXlassFile = new File(System.getProperty("user.dir") + "\\Week_01\\src\\main\\java\\noob\\Hello.ylass");
            FileOutputStream fileOutputStream = new FileOutputStream(newXlassFile);
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //File classFile = new File(System.getProperty("user.dir") + "\\Week_01\\src\\main\\java\\noob\\MyClassToLoad.class");
        File classFile = new File(System.getProperty("user.dir") + "\\Week_01\\src\\main\\java\\noob\\Hello.ylass");
        try {
            FileInputStream fileInputStream = new FileInputStream(classFile);
            int length = fileInputStream.available();
            byte[] bytes = new byte[length];
            fileInputStream.read(bytes);
            for (int i = 0; i < length; i++) {
                bytes[i] = (byte) (bytes[i] - 1);
            }
            return defineClass(name, bytes, 0, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
