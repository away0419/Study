package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
괄호
stack : 스택
 */
public class B9012 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            Stack<Character> stack = new Stack<>();
            boolean check = false;
            for (int j = 0; j < str.length(); j++) {
                char ch = str.charAt(j);
                if (ch == '(') {
                    stack.add(ch);
                } else if (ch == ')') {
                    if (stack.isEmpty()) {
                        sb.append("NO").append("\n");
                        check = true;
                        break;
                    }
                    stack.pop();
                }
            }
            if (!check) {
                if (!stack.isEmpty()) {
                    sb.append("NO").append("\n");
                } else {
                    sb.append("YES").append("\n");
                }
            }
        }
        System.out.println(sb);
    }
}
