package abstractFactory;

public class BurgerSet {
    private final Hamburger hamburger;
    private final Drink drink;

    public BurgerSet(Hamburger hamburger, Drink drink) {
        this.hamburger = hamburger;
        this.drink = drink;
    }

    public Hamburger getHamburger() {
        return hamburger;
    }

    public Drink getDrink() {
        return drink;
    }
}
