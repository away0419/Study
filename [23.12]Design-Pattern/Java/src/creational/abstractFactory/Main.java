package creational.abstractFactory;

public class Main {
    public static void main(String[] args) {
        BurgerSetFactory burgerSetFactory = new BurgerSetFactoryImpl();

        burgerSetFactory.makeSet("Macdonald");
        System.out.println("-------------------------");
        burgerSetFactory.makeSet("BurgerKing");
        System.out.println("-------------------------");
        burgerSetFactory.makeSet("Nothing");

    }
}
