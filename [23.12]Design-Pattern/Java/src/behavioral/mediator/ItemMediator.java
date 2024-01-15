package behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

public class ItemMediator implements Mediator {

    List<Adventurer> list = new ArrayList<>();

    public void addAdventurer(Adventurer adventurer) {
        list.add(adventurer);
    }

    public void forwardRequest(String msg) {
        notice();
        for (Adventurer adventurer : list
        ) {
            System.out.print(adventurer.getName()+"에게 전달 -> ");
            adventurer.receiveRequestToMediator(msg);
        }
    }

    @Override
    public void notice() {
        System.out.println("[중재인 요청 내역 전달]");
    }
}
