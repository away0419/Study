package behavioral.strategy;

public class Main {
    public static void main(String[] args) {
        Adventurer adventurer = new Adventurer();

        adventurer.setSkill(new Fence());
        adventurer.useSkill();

        System.out.println();
        adventurer.setSkill(new Magic());
        adventurer.useSkill();
    }
}
