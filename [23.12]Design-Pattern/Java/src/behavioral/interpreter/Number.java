package behavioral.interpreter;

public class Number implements Expression{
    private double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double interpret() {
        return value;
    }
}
