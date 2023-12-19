package dynamicFactory;

public class Main {
    public static void main(String[] args) {
        DynamicFactory.createFood("Hamburger");
        System.out.println("------------------------");
        DynamicFactory.createFood("Drink");
        System.out.println("------------------------");
        DynamicFactory.createFood("Pizza");
    }
}
