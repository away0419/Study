package behavioral.command;

public class Main {
    public static void main(String[] args) {
        Button button = new Button();
        button.setCommand(new HeaterCommand());
        button.action();

        System.out.println();
        button.setCommand(new LampCommand());
        button.action();
    }
}
