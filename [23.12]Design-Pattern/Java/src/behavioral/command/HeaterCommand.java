package behavioral.command;

public class HeaterCommand implements Command{
    @Override
    public void run() {
        System.out.println("히터 ON");
    }
}
