package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/*
1, 2, 3 더하기 5
N : 주어진 정수
TC : 테스트 케이스
 */
public class B15990 {
    static int TC, MAX;
    static Queue<Integer> q;
    static long[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        TC = Integer.parseInt(br.readLine());
        q = new ArrayDeque<>();

        while (TC-- > 0) {
            int k = Integer.parseInt(br.readLine());
            q.add(k);
            MAX = Math.max(MAX, k);
        }

        dp = new long[MAX + 1][4];
        dp[1][1] = 1;
        dp[2][2] = 1;
        dp[3][1] = 1;
        dp[3][2] = 1;
        dp[3][3] = 1;

        for (int i = 4; i <= MAX; i++) {
            dp[i][1] = (dp[i - 1][3] + dp[i - 1][2]) % 1000000009;
            dp[i][2] = (dp[i - 2][1] + dp[i - 2][3]) % 1000000009;
            dp[i][3] = (dp[i - 3][1] + dp[i - 3][2]) % 1000000009;
        }

        while (!q.isEmpty()) {
            int k = q.poll();
            long res = (dp[k][1] + dp[k][2] + dp[k][3]) % 1000000009;
            sb.append(res).append("\n");
        }

        System.out.println(sb);
    }
}
