public class SomeHeavyWork {
    private static long fibonacci(long number) {
        if ((number == 0) || (number == 1))
            return number;
        else
            return fibonacci(number - 1) + fibonacci(number - 2);
    }
    public static long doWork(){
        return fibonacci(50);
    }
}
