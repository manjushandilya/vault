public class ExceptionCheck {

    public static void main(String[] args) {
        float result = 0;
        try {
            result = divide(2, 1);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(result);

        result = 0;
        try {
            result = divide(2, 0);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(result);
    }

    public static float divide(int a, int b) throws ArithmeticException {
        return a / b;
    }

}
