package behavioral.state;

public class Laptop {
    private PowerState powerState;

    public Laptop() {
        this.powerState = OffState.getInstance();
    }

    public void setPowerState(PowerState powerState) {
        this.powerState = powerState;
    }

    public void powerButtonPush(){
        powerState.powerButtonPush(this);
    }

    public void typeButtonPush(){
        powerState.typeButtonPush();
    }

    void currentStatePrint(){
        System.out.println(powerState.toString());
    }

}
