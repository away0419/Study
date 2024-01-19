package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1234 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int[][][][] dp = new int[N + 1][r + 1][g + 1][b + 1];


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < g; k++) {
                    for (int l = 0; l < b; l++) {
                        if (i == 0) {
                            dp[i][j][k][l] = 1;
                            continue;
                        }

                        if (r-i>=0) dp[i][r][g][b] += dp[i-1][r-i][g][b];
                        if (g-i>=0) dp[i][r][g][b] += dp[i-1][r][g-i][b];
                        if (b-i>=0) dp[i][r][g][b] += dp[i-1][r][g][b-i];

                        if (i % 2 == 0) {
                            int divNum = i/2;
                            if(g-divNum>=0 && b-divNum>=0) dp[i][r][g][b] += dp[i-1][r][g-divNum][b-divNum]*fn(i,divNum);
                            if(r-divNum>=0 && b-divNum>=0) dp[i][r][g][b] += dp[i-1][r-divNum][g][b-divNum]*fn(i,divNum);
                            if(r-divNum>=0 && g-divNum>=0) dp[i][r][g][b] += dp[i-1][r-divNum][g-divNum][b]*fn(i,divNum);
                        }

                        if (i % 3 == 0) {
                            int divNum = i/3;
                            if(r-divNum>=0 && g-divNum>=0 && b-divNum>=0) dp[i][r][g][b] += dp[i-1][r-divNum][g-divNum][b-divNum]*fn(i,divNum) * fn(i-divNum,divNum);
                        }

                    }
                }
            }
        }
    }

    public static int fn(int n, int r) {
        return factorial(n) / (factorial(n - r) * factorial(r));
    }

    public static int factorial(int n) {
        if (n == 1) return 1;
        return n * factorial(n - 1);
    }

}
