package behavioral.interpreter;

public class Subtraction implements Expression{
    private Expression leftOperand;
    private Expression rightOperand;

    public Subtraction(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public double interpret() {
        return leftOperand.interpret() - rightOperand.interpret();
    }
}
