package behavioral.interpreter

class Addition(private val leftExpression: Expression, private val rightExpression: Expression): Expression {

    override fun interpret(): Double {
        return leftExpression.interpret() + rightExpression.interpret();
    }
}