package behavioral.command;

public class Button {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action(){
        command.run();
    }
}
