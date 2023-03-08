package factoryMethod;

/*
 * 굳이 팩토리를 거치지 않고 객체 자체에 객체 생성을 하는 static method가 있는 걸 정적 팩토리 메소드라 한다.
 */
public class Cola implements Beverage {

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	public static Cola getCola() {

		return new Cola();
	}

	public static Beverage getBeverage() {

		return (Beverage) new Cola();
	}

}
