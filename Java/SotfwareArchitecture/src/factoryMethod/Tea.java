package factoryMethod;

/*
 * 구체적인 객체 (티)
 */
public class Tea implements Beverage{

	@Override
	public void prepare() {
		System.out.println("티 만들기");
	}


}
