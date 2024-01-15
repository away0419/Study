package creational.factoryMethod;

public class Main {
    public static void main(String[] args){
        DrinkFactory drink1 = new CoffeeFactoryImpl();
        DrinkFactory drink2 = new TeaFactoryImpl();

        drink1.makeDrink();
        drink2.makeDrink();
    }
}
