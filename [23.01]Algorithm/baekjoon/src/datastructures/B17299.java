package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class B17299 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Integer> dq = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        int n = Integer.parseInt(br.readLine());
        int[] answer = new int[n];
        int[] arr = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }

        for (int i = n - 1; i >= 0; i--) {
            int value = map.get(arr[i]);
            int res = -1;

            while (!dq.isEmpty()) {
                int cur = dq.peek();
                int curValue = map.get(cur);
                if (curValue > value) {
                    res = cur;
                    break;
                }

                dq.pop();
            }

            dq.push(arr[i]);

            answer[i] = res;
        }

        StringBuilder sb = new StringBuilder();
        for (int i : answer) {
            sb.append(i).append(" ");
        }

        System.out.println(sb);

    }
}
