package behavioral.command;

public class LampCommand implements Command{
    @Override
    public void run() {
        System.out.println("램프 ON");
    }
}
