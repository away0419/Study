package behavioral.interpreter

class Division(private val leftExpression: Expression, private val rightExpression: Expression): Expression {

    override fun interpret(): Double {
        if (rightExpression.interpret() == 0.0) throw ArithmeticException("Division by zero")
        return leftExpression.interpret() / rightExpression.interpret()
    }
}