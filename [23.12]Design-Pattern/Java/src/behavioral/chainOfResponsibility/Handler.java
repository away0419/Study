package behavioral.chainOfResponsibility;

public interface Handler {
    void setNextHandler(Handler handler);
    void process(String authority);
}
