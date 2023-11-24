package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1520 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        int[][] dp = new int[N][M];
        int[] idx = {-1, 1, 0, 0};
        int[] idy = {0, 0, -1, 1};

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }

        dp[0][0] = 1;
        int answer = fn(N - 1, M - 1, N, M, map, dp, idx, idy);

        System.out.println(answer);

    }

    public static int fn(int x, int y, int N, int M, int[][] map, int[][] dp, int[] idx, int[] idy) {

        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        dp[x][y] = 0;

        for (int i = 0; i < 4; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx < 0 || sy < 0 || sx >= N || sy >= M || map[sx][sy] <= map[x][y]) {
                continue;
            }

            dp[x][y] += fn(sx, sy, N, M, map, dp, idx, idy);
        }

        return dp[x][y];
    }
}
