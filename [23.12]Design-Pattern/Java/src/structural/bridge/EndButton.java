package structural.bridge;

public class EndButton extends Button{
    public EndButton(Color color) {
        super(color);
    }

    @Override
    public void action() {
        System.out.println("End!!!");
    }
}
