package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14722_2 {
    public static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[][] arr = new int[N+1][N+1];
        int[][][] dp = new int[3][N][N];
        int[] idx = {0, 1};
        int[] idy = {1, 0};


        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        fn(N, 0, 0, 2, idx, idy, arr, dp);
        dp[2][0][0] = 1;
        System.out.println(answer);

    }


    public static void fn(int N, int x, int y, int milk, int[] idx, int[] idy, int[][] arr, int[][][] dp) {
        if (x == N - 1 && y == N - 1) {
            answer = Math.max(answer, dp[milk][x][y]);
            return;
        }

        for (int i = 0; i < 2; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx >= N || sy >= N) {
                continue;
            }

            if (milk == 2 && arr[sx][sy] == 0) {
                dp[arr[sx][sy]][sx][sy] = Math.max(dp[arr[sx][sy]][sx][sy], dp[milk][x][y] + 1);
                fn(N, sx, sy, arr[sx][sy], idx, idy, arr, dp);
            } else if (milk == 1 && arr[sx][sy] == 2) {
                dp[arr[sx][sy]][sx][sy] = Math.max(dp[arr[sx][sy]][sx][sy], dp[milk][x][y] + 1);
                fn(N, sx, sy, arr[sx][sy], idx, idy, arr, dp);
            } else if (milk == 0 && arr[sx][sy] == 1) {
                dp[arr[sx][sy]][sx][sy] = Math.max(dp[arr[sx][sy]][sx][sy], dp[milk][x][y] + 1);
                fn(N, sx, sy, arr[sx][sy], idx, idy, arr, dp);
            } else {
                dp[milk][sx][sy] = Math.max(dp[milk][sx][sy], dp[milk][x][y]);
                fn(N, sx, sy, arr[sx][sy], idx, idy, arr, dp);
            }

        }

    }

}
