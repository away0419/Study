package retry2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1234 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        int G = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        long[][][][] dp = new long[N + 1][R + 1][G + 1][B + 1];

        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= R; j++) {
                for (int k = 0; k <= G; k++) {
                    for (int l = 0; l <= B; l++) {
                        if (i == 0) {
                            dp[i][j][k][l] = 1;
                            continue;
                        }

                        if (j - i >= 0) {
                            dp[i][j][k][l] += dp[i - 1][j - i][k][l];
                        }
                        if (k - i >= 0) {
                            dp[i][j][k][l] += dp[i - 1][j][k - i][l];
                        }
                        if (l - i >= 0) {
                            dp[i][j][k][l] += dp[i - 1][j][k][l - i];
                        }

                        if (i % 2 == 0) {
                            int ban = i / 2;

                            if ((j - ban) >= 0 && (k - ban) >= 0) dp[i][j][k][l] += dp[i - 1][j - ban][k - ban][l] * combination(i, ban);
                            if ((l - ban) >= 0 && (k - ban) >= 0) dp[i][j][k][l] += dp[i - 1][j][k - ban][l - ban] * combination(i, ban);
                            if ((j - ban) >= 0 && (l - ban) >= 0) dp[i][j][k][l] += dp[i - 1][j - ban][k][l - ban] * combination(i, ban);

                        }
                        if (i % 3 == 0) {
                            int s = i / 3;

                            if ((j - s) >= 0 && (k - s) >= 0 && (l - s) >= 0) {
                                dp[i][j][k][l] += dp[i - 1][j - s][k - s][l - s] * combination(i, s) * combination(i-s, s);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(dp[N][R][G][B]);

    }

    public static long combination(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }


    public static long factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return factorial(n - 1) * n;

    }

}
