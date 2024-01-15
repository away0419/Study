package structural.decorator;

public class CheeseDecorator extends HamburgerDecorator{
    public CheeseDecorator(Hamburger hamburger) {
        super(hamburger);
    }

    @Override
    public String getName() {
        return "치즈 " + super.getName();
    }
}
