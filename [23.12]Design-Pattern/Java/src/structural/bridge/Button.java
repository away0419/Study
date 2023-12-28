package structural.bridge;

public abstract class Button {
    Color color;

    protected Button(Color color){
        this.color = color;
    }

    public abstract void action();
}
