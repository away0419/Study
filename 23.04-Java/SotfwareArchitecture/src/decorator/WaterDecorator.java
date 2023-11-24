package decorator;

//물을 추가해주는 클래스
public class WaterDecorator extends Decorator {
  public WaterDecorator(Component coffeeComponent) {
      super(coffeeComponent);
  }
  
  @Override
  public String add() {
      // TODO Auto-generated method stub
      return super.add() + " + 물";
  }
}