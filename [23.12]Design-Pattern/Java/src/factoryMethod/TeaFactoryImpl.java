package factoryMethod;

public class TeaFactoryImpl implements DrinkFactory{
    @Override
    public Drink makeDrink() {
        System.out.println("makeTea");
        return new Tea();
    }
}
