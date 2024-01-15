package behavioral.interpreter;

public class Addition implements Expression{

    private Expression leftOperand;
    private Expression rightOperand;


    public Addition(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public double interpret() {
        return leftOperand.interpret() + rightOperand.interpret();
    }
}