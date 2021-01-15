package noob;

public class MyClassToLoad {
    private static final int staticFinalInt = 4321;
    private static int staticInt = 321;
    private final int finalInt = 1234;
    private int normalInt = 123;

    public static void staticMethod(String s) {
        System.out.println(s);
//        staticInt = s.hashCode() + 123;
//        System.out.println(staticInt);
//        String staticMethodS1 = "asdf";
//        staticMethodS1 = staticMethodS1 + "vs";
//        staticMethodS1 = staticMethodS1 + s;
//        System.out.println(staticMethodS1);
    }

    public int normalMethod() {
        System.out.println(normalInt);
        normalInt += 1234;
        System.out.println(normalInt);
        int m1Int = 11111;
        m1Int += normalInt;
        return m1Int;
    }

    public final void finalMethod() {
        staticMethod("123");
        System.out.println(finalInt + "lkj");
    }
    static {
        staticMethod(""+System.currentTimeMillis());
    }
}
