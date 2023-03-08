package factoryMethod;

/*
 * 구체적인 객체 (커피)
 */
public class Coffee implements Beverage{

	@Override
	public void prepare() {
		System.out.println("커피 만들기");
	}
	
}
