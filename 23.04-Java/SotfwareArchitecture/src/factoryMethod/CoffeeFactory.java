package factoryMethod;

/*
 * 실제 커피 객체를 만들어 반환해주는 팩토리 클래스
 */
public class CoffeeFactory implements BeverageFactory{

	@Override
	public Beverage createBeverage() {
		return new Coffee();
	}

}
