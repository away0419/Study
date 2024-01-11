package behavioral.state;

public class Main {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();

        System.out.println("[초기 상태]");
        laptop.currentStatePrint();
        laptop.typeButtonPush();

        System.out.println();
        System.out.println("[전원 버튼 클릭]");
        laptop.powerButtonPush();
        laptop.currentStatePrint();
        laptop.typeButtonPush();

        System.out.println();
        System.out.println("[전원 버튼 클릭]");
        laptop.powerButtonPush();
        laptop.currentStatePrint();
        laptop.typeButtonPush();

    }
}
