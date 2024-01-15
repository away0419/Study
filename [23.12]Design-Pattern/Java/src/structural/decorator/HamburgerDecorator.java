package structural.decorator;

public abstract class HamburgerDecorator implements Hamburger{
    private Hamburger hamburger;

    public HamburgerDecorator(Hamburger hamburger) {
        this.hamburger = hamburger;
    }

    @Override
    public String getName() {
        return hamburger.getName();
    }
}
