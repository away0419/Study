package structural.decorator;

public class BulgogiDecorator extends HamburgerDecorator{
    public BulgogiDecorator(Hamburger hamburger) {
        super(hamburger);
    }

    @Override
    public String getName() {
        return "불고기 " + super.getName();
    }
}
