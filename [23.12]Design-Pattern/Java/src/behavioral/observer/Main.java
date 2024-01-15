package behavioral.observer;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        Adventurer adventurer1 = new Adventurer("전사");
        Adventurer adventurer2 = new Adventurer("법사");
        Adventurer adventurer3 = new Adventurer("도적");

        store.registerObserver(adventurer1);
        store.registerObserver(adventurer2);
        store.registerObserver(adventurer3);
        store.sendNotice("유니크 아이템이 등록되었습니다.");

        System.out.println();
        store.removeObserver(adventurer1);
        store.sendNotice("에픽 아이템이 등록되었습니다.");

    }
}
