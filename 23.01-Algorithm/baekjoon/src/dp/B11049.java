package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B11049 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[][] dp = new int[N + 1][N + 1];
        int[][] map = new int[N + 1][2];
        int INF = Integer.MAX_VALUE;

        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            map[i][0] = Integer.parseInt(st.nextToken());
            map[i][1] = Integer.parseInt(st.nextToken());
        }

        int answer = fn(1, N, map, dp, INF);
        System.out.println(answer);
    }

    public static int fn(int s, int e, int[][] map, int[][] dp, int INF) {
        if (s == e) {
            return 0;
        }

        if (dp[s][e] != INF) {
            return dp[s][e];
        }

        for (int i = s; i < e; i++) {
            dp[s][e] = Math.min(fn(s, i, map, dp, INF) + fn(i+1, e, map, dp, INF) + map[s][0] * map[i+1][0] * map[e][1], dp[s][e]);
        }

        return dp[s][e];
    }
}
