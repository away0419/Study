package behavioral.mediator;

public interface Mediator {
    void notice();
    void forwardRequest(String msg);
}
