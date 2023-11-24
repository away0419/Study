package level3_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1937 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        int[][] dp = new int[n][n];
        int[] idx = {0, 0, -1, 1};
        int[] idy = {1, -1, 0, 0};
        StringTokenizer st;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int res = fn(i, j, idx, idy, n, map, dp);
                answer = Math.max(answer, res);
            }
        }

        System.out.println(answer);

    }

    public static int fn(int x, int y, int[] idx, int[] idy, int n, int[][] map, int[][] dp) {
        if (dp[x][y] != 0) {
            return dp[x][y];
        }

        dp[x][y] = 1;

        for (int i = 0; i < 4; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx < 0 || sy < 0 || sx >= n || sy >= n || map[x][y] >= map[sx][sy]) {
                continue;
            }

            dp[x][y] = Math.max(fn(sx, sy, idx, idy, n, map, dp) + 1, dp[x][y]);

        }

        return dp[x][y];
    }
}
