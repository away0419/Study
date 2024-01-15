package behavioral.interpreter

import java.util.*

fun main() {
    println("사칙연산 표현식을 입력하세요")
    val userInput = readln()

    val expression = buildExpression(userInput)

    try {
        val result = expression.interpret()
        println("결과: $result")
    } catch (e: Exception) {
        println("오류 발생: ${e.message}")
    }
}

private fun buildExpression(userInput: String): Expression {
    val tokens = userInput.split(" ")
    val expressionStack = Stack<Expression>()
    val operatorStack = Stack<String>()

    for (token in tokens) {
        if (isNumeric(token)) {
            expressionStack.push(Number(token.toDouble()))
        } else if ("+-*/".contains(token)) {
            while (operatorStack.isNotEmpty() && hasPrecedence(token, operatorStack.peek())) {
                val topOperator = operatorStack.pop()
                val rightOperand = expressionStack.pop()
                val leftOperand = expressionStack.pop()
                expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator))
            }
            operatorStack.push(token)
        } else {
            throw IllegalArgumentException("잘못된 표현식입니다: $token")
        }
    }

    while (operatorStack.isNotEmpty()) {
        val topOperator = operatorStack.pop()
        val rightOperand = expressionStack.pop()
        val leftOperand = expressionStack.pop()
        expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator))
    }

    return if (expressionStack.size == 1) {
        expressionStack.pop()
    } else {
        throw IllegalArgumentException("잘못된 표현식입니다.")
    }
}

private fun createOperatorExpression(left: Expression, right: Expression, operator: String): Expression {
    return when (operator) {
        "+" -> Addition(left, right)
        "-" -> Subtraction(left, right)
        "*" -> Multiplication(left, right)
        "/" -> Division(left, right)
        else -> throw IllegalArgumentException("지원되지 않는 연산자입니다: $operator")
    }
}

private fun isNumeric(str: String): Boolean {
    return try {
        str.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

private fun hasPrecedence(op1: String, op2: String): Boolean {
    return (!op1.equals("*") && !op1.equals("/")) || (!op2.equals("+") && !op2.equals("-"))
}