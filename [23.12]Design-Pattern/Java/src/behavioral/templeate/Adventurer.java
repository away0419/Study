package behavioral.templeate;

public abstract class Adventurer {

    public final void attack() {
        System.out.println("공격 전 준비 동작");
        action();
        System.out.println("공격 시작");
    }

    protected abstract void action();
}
