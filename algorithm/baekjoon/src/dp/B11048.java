package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
이동하기
N, M : 행렬
map[][] : 주어진 배열
 */
public class B11048 {
    static int N, M, map[][], dp[][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        dp = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }

        int res = fn(0, 0);
        System.out.println(res);
    }

    public static int fn(int x, int y) {
        if (x == N - 1 && y == M - 1) {
            return dp[x][y] = map[x][y];
        }
        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        if (x + 1 < N) {
            dp[x][y] = Math.max(dp[x][y], fn(x + 1, y));
        }
        if (y + 1 < M) {
            dp[x][y] = Math.max(dp[x][y], fn(x, y + 1));
        }


        return dp[x][y] += map[x][y];
    }
}
