package structural.decorator;

public class Main {
    public static void main(String[] args) {
        Hamburger hamburger = new BasicHamBurger();
        System.out.println(hamburger.getName());
        System.out.println();

        hamburger = new CheeseDecorator(hamburger);
        System.out.println("치즈 토핑 추가");
        System.out.println(hamburger.getName());
        System.out.println();

        hamburger = new BulgogiDecorator(hamburger);
        System.out.println("불고기 토핑 추가");
        System.out.println(hamburger.getName());
        System.out.println();

        hamburger = new BulgogiDecorator(hamburger);
        System.out.println("불고기 토핑 추가");
        System.out.println(hamburger.getName());
        System.out.println();

        hamburger = new CheeseDecorator(hamburger);
        System.out.println("치즈 토핑 추가");
        System.out.println(hamburger.getName());

    }
}
