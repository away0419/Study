package behavioral.state;

public class OnState implements PowerState {

    private OnState() {
    }

    private static class SingleInstanceHolder {
        private static final OnState INSTANCE = new OnState();
    }

    public static OnState getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    @Override
    public void powerButtonPush(Laptop laptop) {
        System.out.println("노트북 전원 OFF");
        laptop.setPowerState(OffState.getInstance());
    }

    @Override
    public void typeButtonPush() {
        System.out.println("타자 입력");
    }

    @Override
    public String toString() {
        return "전원 상태 ON";
    }
}
