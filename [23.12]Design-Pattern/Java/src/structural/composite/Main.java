package structural.composite;

public class Main {

    public static void main(String[] args) {
        Box normalBox = new NormalBox("기본 상자", 10000);
        Box equipmentBox = new NormalBox("장비 상자", 2000);
        Item sword = new NormalItem("검", 500);
        Item shield = new NormalItem("방패", 500);
        Item gold = new NormalItem("금", 500);

        equipmentBox.addItem(sword);
        equipmentBox.addItem(shield);
        normalBox.addItem(equipmentBox);
        normalBox.addItem(gold);

        System.out.println(normalBox.getAllPrice());
        System.out.println(normalBox.getItems());
    }
}
