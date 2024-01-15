package creational.builder;

public class Main {
    public static void main(String[] args) {
//        Drink drink = new Drink().name("커피").size("라지").price("만원");
        Drink drink = new DrinkBuilder().name("커피").size("라지").price("만원").build();
        Hamburger hamburger = new Hamburger.HamburgerBuilder().name("맥도날드버거").size("라지").price(10000).build();

        System.out.println(drink.toString());
        System.out.println("------------------------------");
        System.out.println(hamburger.toString());

    }
}
