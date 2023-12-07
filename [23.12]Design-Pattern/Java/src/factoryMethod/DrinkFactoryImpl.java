package factoryMethod;

public class DrinkFactoryImpl implements  DrinkFactory{
    @Override
    public Drink makeDrink() {
        return new Drink();
    }
}
