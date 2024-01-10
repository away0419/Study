package behavioral.mediator;

import structural.facade.Person;

public class Adventurer {

    private String name;
    private Mediator mediator;


    public Adventurer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMediator(ItemMediator mediator) {
        mediator.addAdventurer(this);
        this.mediator = mediator;
    }

    public void sendRequestToMediator(String msg) {
        mediator.forwardRequest(msg);
    }

    public void receiveRequestToMediator(String msg) {
        System.out.println("전달 받은 내용: " + msg);
    }

}
