package behavioral.observer;

public class Adventurer implements Observer{
    private String name;

    public Adventurer(String name) {
        this.name = name;
    }

    @Override
    public void receiveNotice(String msg) {
        System.out.println(name +"님 알람이 도착했습니다. 내용: "+ msg);
    }
}
