package behavioral.interpreter;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("사칙연산 표현식을 입력하세요:");
        String userInput = scanner.nextLine();

        Expression expression = buildExpression(userInput);

        try {
            double result = expression.interpret();
            System.out.println("결과: " + result);
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        }
    }

    private static Expression buildExpression(String userInput) {
        String[] tokens = userInput.split(" ");
        Stack<Expression> expressionStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                expressionStack.push(new Number(Double.parseDouble(token)));
            } else if ("+-*/".contains(token)) {
                while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek())) {
                    String topOperator = operatorStack.pop();
                    Expression rightOperand = expressionStack.pop();
                    Expression leftOperand = expressionStack.pop();
                    expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator));
                }
                operatorStack.push(token);
            } else {
                throw new IllegalArgumentException("잘못된 표현식입니다: " + token);
            }
        }

        while (!operatorStack.isEmpty()) {
            String topOperator = operatorStack.pop();
            Expression rightOperand = expressionStack.pop();
            Expression leftOperand = expressionStack.pop();
            expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator));
        }

        if (expressionStack.size() == 1) {
            return expressionStack.pop();
        } else {
            throw new IllegalArgumentException("잘못된 표현식입니다.");
        }
    }

    private static Expression createOperatorExpression(Expression left, Expression right, String operator) {
        return switch (operator) {
            case "+" -> new Addition(left, right);
            case "-" -> new Subtraction(left, right);
            case "*" -> new Multiplication(left, right);
            case "/" -> new Division(left, right);
            default -> throw new IllegalArgumentException("지원되지 않는 연산자입니다: " + operator);
        };
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean hasPrecedence(String op1, String op2) {
        return (!op1.equals("*") && !op1.equals("/")) || (!op2.equals("+") && !op2.equals("-"));
    }

}
