package behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class Store implements Subject {

    private List<Observer> subscirbers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        subscirbers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        subscirbers.remove(observer);
    }

    @Override
    public void sendNotice(String msg) {
        System.out.println("[구독자 메시지 전달 시작]");
        for (Observer o :
                subscirbers) {
            o.receiveNotice(msg);
        }
    }
}
