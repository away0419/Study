public class Function {
    private Function() {

    }

    public static Function functionFactory() {
        return new Function();
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }
}
