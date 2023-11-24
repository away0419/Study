package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
추월
N : 차 수

 */
public class B2002 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, Integer> map = new HashMap<>();
        int N = Integer.parseInt(br.readLine());
        int answer = 0;
        for (int i = 0; i < N; i++) {
            map.put(br.readLine(), i);
        }

        Stack<Integer> stack = new Stack<>();
        int start = 0;
        for (int i = 0; i < N; i++) {
            int k = map.get(br.readLine());
            if (start > k) {
                while (!stack.isEmpty()) {
                    int s = stack.peek();
                    if (s > k) {
                        answer++;
                        stack.pop();
                    } else {
                        break;
                    }
                }
            }
            stack.add(k);
            start = k;
        }

        System.out.println(answer);
    }


}
