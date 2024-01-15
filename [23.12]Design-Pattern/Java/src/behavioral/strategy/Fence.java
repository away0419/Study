package behavioral.strategy;

public class Fence implements Skill{
    @Override
    public void active() {
        System.out.println("검술 발동");
    }
}
