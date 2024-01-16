package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14722 {
    public static class Info {
        int x;
        int y;
        int prev;

        public Info(int x, int y, int prev) {
            this.x = x;
            this.y = y;
            this.prev = prev;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];
        int[][] dp = new int[N][N];
        int[] idx = {0, 1};
        int[] idy = {1, 0};
        dp[N-1][N-1] = 1;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        fn(map, dp, idx, idy, N, 0, 0, map[0][0]);
        System.out.println(dp[0][0]);
    }

    public static int fn(int[][] map, int[][] dp, int[] idx, int[] idy, int N, int x, int y, int prev) {

        if (dp[x][y] != 0) {
            return dp[x][y];
        }


        for (int i = 0; i < 2; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx < 0 || sy < 0 || sx >= N || sy >= N) continue;
            if ((prev+ 1) % 3 != map[sx][sy] ) {
                dp[x][y] = Math.max(dp[x][y], fn(map, dp, idx, idy, N, sx, sy, prev));
            } else {
                dp[x][y] = Math.max(dp[x][y], fn(map, dp, idx, idy, N, sx, sy, map[sx][sy]) + 1);
            }
        }

        return dp[x][y];
    }
}
