package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/*
1, 2, 3 더하기 3
N : 주어지는 수
dp[] : 경우의 수
 */
public class B15988 {
    static int TC, N, MAX;
    static long dp[];
    static Queue<Integer> q;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TC = Integer.parseInt(br.readLine());
        q = new ArrayDeque<>();
        while (TC-- > 0) {
            N = Integer.parseInt(br.readLine());
            MAX = Math.max(MAX, N);
            q.add(N);
        }
        dp = new long[MAX + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        for (int i = 4; i <= MAX; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2] + dp[i - 3]) % 1000000009;
        }
        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            int target = q.poll();
            sb.append(dp[target]).append("\n");
        }

        System.out.println(sb);
    }


}
