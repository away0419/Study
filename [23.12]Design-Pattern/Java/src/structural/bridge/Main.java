package structural.bridge;

public class Main {
    public static void main(String[] args) {
        Button startButton = new StartButton(new Red());
        Button endButton = new EndButton(new Blue());

        System.out.println("StartButton click");
        startButton.color.getColor();
        startButton.action();

        System.out.println("------------------------");

        System.out.println("EndButton click");
        endButton.color.getColor();
        endButton.action();
    }
}
