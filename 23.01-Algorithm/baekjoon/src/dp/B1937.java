package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1937 {
    public static class Info {
        int x;
        int y;
        int length;

        public Info(int x, int y, int length) {
            this.x = x;
            this.y = y;
            this.length = length;
        }
    }

    public static void main(String[] args) throws IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];
        int[][] dp = new int[N][N];
        int[] idx = {0, 0, -1, 1};
        int[] idy = {1, -1, 0, 0};

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int res = fn(i, j, N, map, dp, idx, idy);
                answer = Math.max(answer, res);
            }
        }

        System.out.println(answer);

    }

    public static int fn(int x, int y, int N, int[][] map, int[][] dp, int[] idx, int[] idy) {
        if (dp[x][y] != 0) {
            return dp[x][y];
        }
        dp[x][y] = 1;

        for (int i = 0; i < 4; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx < 0 || sy < 0 || sx >= N || sy >= N || map[x][y] <= map[sx][sy]) {
                continue;
            }

            dp[x][y] = Math.max(dp[x][y], fn(sx, sy, N, map, dp, idx, idy) + 1);

        }

        return dp[x][y];
    }
}
