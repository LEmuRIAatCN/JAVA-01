package noob;

public class Main {
    private static final int finalInt = 321;
    private static int staticInt = 1234;
    private int normalInt = 123;

    public Main() {
        System.out.println(staticInt);
    }

    public Main(String s) {
        System.out.println(staticInt);
    }

    public Main(int c) {
        normalInt = c;
        System.out.println(staticInt);
    }

    public static void main(String[] s) {
        int uu = 1;
        uu = uu + 1000;
        uu = uu * 3;
        uu = uu % 33;
        uu = uu - 123;
        Main main = new Main();
        main.normalInt = main.normalInt + uu;
        main.doSth(123);
        for(int i=0;i<10;i++){
            if(i%2==0){
                main.doSth(2);
            }
            if(i%3==0){
                main.doSth(3);
            }
        }
    }

    public void doSth(int x) {
        normalInt = x + 123;
        staticInt = normalInt;
        int y = 123;
        y = y + normalInt;
    }

}