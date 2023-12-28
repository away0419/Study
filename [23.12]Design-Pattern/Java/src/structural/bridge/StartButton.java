package structural.bridge;

public class StartButton extends Button{

    public StartButton(Color color) {
        super(color);
    }

    @Override
    public void action() {
        System.out.println("Start!!!");
    }
}
