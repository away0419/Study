package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
괄호의 값
statck<Character> : 주어진 문자가 들어갈 스택
answer : 답
res : 계산 값

 */
public class B2504 {
    static int answer, res;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        Stack<Character> stack = new Stack<>();
        res = 1;
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            if (ch == '(') {
                res *= 2;
                stack.add(ch);
            } else if (ch == '[') {
                res *= 3;
                stack.add(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.peek() != '(') {
                    answer = 0;
                    break;
                }
                if (str.charAt(i - 1) == '(') {
                    answer += res;
                }
                res /= 2;
                stack.pop();
            } else if (ch == ']') {
                if (stack.isEmpty() || stack.peek() != '[') {
                    answer = 0;
                    break;
                }
                if (str.charAt(i - 1) == '[') {
                    answer += res;
                }
                res /= 3;
                stack.pop();
            }
        }
        if (!stack.isEmpty()) answer = 0;
        System.out.println(answer);
    }
}
