package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2698 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[][][] dp = new int[101][101][2];

        dp[1][0][0] = 1;
        dp[1][0][1] = 1;

        for (int k = 0; k <= 100; k++) {
            for (int n = 2; n <= 100; n++) {
                if (k == 0) {
                    dp[n][k][1] = dp[n - 1][k][0];

                } else {
                    dp[n][k][1] = dp[n - 1][k - 1][1] + dp[n - 1][k][0];
                }
                dp[n][k][0] = dp[n - 1][k][0] + dp[n - 1][k][1];
            }
        }

        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            sb.append(dp[n][k][0] + dp[n][k][1] + "\n");
        }
        System.out.println(sb);

    }
}
