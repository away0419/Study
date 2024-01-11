package behavioral.state;

public class OffState implements PowerState {
    private OffState() {
    }

    private static class SingleInstanceHolder {
        private static final OffState INSTANCE = new OffState();
    }

    public static OffState getInstance() {
        return OffState.SingleInstanceHolder.INSTANCE;
    }

    @Override
    public void powerButtonPush(Laptop laptop) {
        System.out.println("노트북 전원 ON");
        laptop.setPowerState(OnState.getInstance());
    }

    @Override
    public void typeButtonPush() {
        System.out.println("무반응");
    }

    @Override
    public String toString() {
        return "전원 상태 OFF";
    }
}
