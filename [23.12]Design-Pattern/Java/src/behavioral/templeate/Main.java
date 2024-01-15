package behavioral.templeate;

public class Main {
    public static void main(String[] args) {
        Adventurer adventurer1 = new Warrior();
        Adventurer adventurer2 = new Wizard();

        adventurer1.attack();
        System.out.println();
        adventurer2.attack();
    }
}
