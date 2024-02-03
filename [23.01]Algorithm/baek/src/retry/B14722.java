package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14722 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] arr = new int[N][N];
        int[][][] dp = new int[N][N][3];
        int[] idx = {1, 0};
        int[] idy = {0, 1};
        StringTokenizer st;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int answer = fn(N, arr, dp, idx, idy, 0, 0, 0);
        System.out.println(answer);
    }

    public static int fn(int N, int[][] arr, int[][][] dp, int[] idx, int[] idy, int x, int y, int milk) {

        if (dp[x][y][milk] != 0)
            return dp[x][y][milk];

        int count = dp[x][y][milk];
        int nextMilk = milk;
        if (arr[x][y] == milk) { // 다음에 먹어야할우유가 맞다면 count++;
            nextMilk = (nextMilk + 1) % 3;
            count++;
        }

        int max = 0;
        for (int i = 0; i < 2; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx >= N || sy >= N) {
                continue;
            }

            max = Math.max(max, fn(N, arr, dp, idx, idy, sx, sy, nextMilk));
        }


        return dp[x][y][milk] = count+max;
    }

}
