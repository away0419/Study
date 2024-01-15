package creational.enumFactoryMethod;

public class Main {
    public static void main(String[] args) {
        System.out.println(EnumFoodFactory.DRINK.getName());
        EnumFoodFactory.DRINK.createFood();

        System.out.println("---------------------------------------");

        System.out.println(EnumFoodFactory.HAMBURGER.getName());
        EnumFoodFactory.HAMBURGER.createFood();


    }
}
