package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/*
1, 2, 3 더하기

 */
public class B9095 {
    static int N, MAX, TC;
    static int[] dp;
    static Queue<Integer> q;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TC = Integer.parseInt(br.readLine());
        q = new ArrayDeque<>();
        while (TC-- > 0) {
            int k = Integer.parseInt(br.readLine());
            q.add(k);
            MAX = Math.max(MAX, k);
        }
        dp = new int[MAX + 1];

        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        for (int i = 4; i <= MAX; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            sb.append(dp[q.poll()]).append("\n");
        }

        System.out.println(sb);

    }

}
