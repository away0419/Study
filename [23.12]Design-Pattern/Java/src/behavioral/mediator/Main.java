package behavioral.mediator;

public class Main {
    public static void main(String[] args) {
        Adventurer adventurer1 = new Adventurer("전사");
        Adventurer adventurer2 = new Adventurer("마법사");
        Adventurer adventurer3 = new Adventurer("도적");
        ItemMediator mediator = new ItemMediator();

        adventurer1.setMediator(mediator);
        adventurer2.setMediator(mediator);
        adventurer3.setMediator(mediator);

        adventurer1.sendRequestToMediator("포션 팝니다.");


    }
}
