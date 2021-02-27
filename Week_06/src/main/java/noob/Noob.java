package noob;

public class Noob {
    public void case3(){
        {
            byte[] buffer = new byte[20*1024*1024];
        }
        System.gc();
    }
    public void case4(){
        {
            byte[] buffer = new byte[10*1024*1024];
        }
        int value = 10;
        System.gc();
    }

    public static void main(String[] args){
        Noob noob = new Noob();
        noob.case3();
        noob.case4();
    }
}
