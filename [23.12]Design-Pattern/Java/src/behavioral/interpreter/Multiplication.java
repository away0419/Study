package behavioral.interpreter;

public class Multiplication implements Expression{

    private Expression leftOperand;
    private Expression rightOperand;

    public Multiplication(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public double interpret() {
        return leftOperand.interpret() * rightOperand.interpret();
    }
}
