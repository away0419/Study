package factoryMethod;

public class CoffeeFactoryImpl implements DrinkFactory{
    @Override
    public Drink makeDrink() {
        System.out.println("makeCoffee");
        return new Coffee();
    }
}
