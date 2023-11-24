package decorator;

public class Main {

    public static void main(String[] args) {
        Component espresso = new BaseComponent();
        System.out.println("에스프레소 : " + espresso.add());
        
        Component americano = new WaterDecorator(new BaseComponent());
        System.out.println("아메리카노 : " + americano.add());
        
        Component latte = new MilkDecorator(new WaterDecorator(new BaseComponent()));
        System.out.println("라떼 : " + latte.add());
    }
}
