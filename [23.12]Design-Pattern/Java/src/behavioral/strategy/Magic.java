package behavioral.strategy;

public class Magic implements Skill{
    @Override
    public void active() {
        System.out.println("마법 발동");
    }
}
