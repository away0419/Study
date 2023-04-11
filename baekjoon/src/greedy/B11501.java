package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/*
주식
N : 날짜 수
stack : 지금까지 쌓인 수
TC : 테스트 케이스
 */
public class B11501 {
    static Stack<Integer> stack;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (tc-- > 0) {
            int n = Integer.parseInt(br.readLine());
            stack = new Stack<>();
            st = new StringTokenizer(br.readLine());
            long answer = 0;
            for (int i = 0; i < n; i++) {
                stack.add(Integer.parseInt(st.nextToken()));
            }

            int end = stack.pop();
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                int k = stack.pop();
                if (k < end) {
                    answer += end - k;
                } else if (k > end) {
                    end = k;
                }
            }
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }


}
