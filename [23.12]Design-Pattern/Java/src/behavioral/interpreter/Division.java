package behavioral.interpreter;

public class Division implements Expression {
    private Expression leftOperand;
    private Expression rightOperand;

    public Division(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public double interpret() {
        if (rightOperand.interpret() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return leftOperand.interpret() / rightOperand.interpret();
    }
}
