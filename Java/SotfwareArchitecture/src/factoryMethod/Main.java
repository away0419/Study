package factoryMethod;

/*
 * 만약 Coffee 클래스를 없애고 Juice 클래스를 만들었다 하더라도 Main 클래스의 소스를 바꿀 필요가 없음.
 * coffeeFactory 안에 있는 createBeverage()에서 return new Coffee()를 return new Juice()로 변경하면 됨.
 * 이게 바로 FactoryMethod 패턴
 */
public class Main {

	public static void main(String[] args) {
		BeverageFactory coffeeFactory = new CoffeeFactory();
		BeverageFactory teaFactory = new TeaFactory();
		Beverage coffee = coffeeFactory.createBeverage();
		Beverage tea = teaFactory.createBeverage();

		coffee.prepare(); // "커피를 만들었습니다." 출력
		tea.prepare(); // "차를 만들었습니다." 출력

	}

}
