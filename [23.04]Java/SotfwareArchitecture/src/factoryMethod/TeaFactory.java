package factoryMethod;

public class TeaFactory implements BeverageFactory {

	@Override
	public Beverage createBeverage() {
		return new Tea();
	}

}
