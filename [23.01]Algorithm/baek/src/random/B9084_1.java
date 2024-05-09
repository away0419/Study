package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B9084_1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            int[] coins = new int[N];
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(br.readLine());

            for (int i = 0; i < N; i++) {
                coins[i] = Integer.parseInt(st.nextToken());
            }

            sb.append(fn(N, coins, M)).append("\n");
        }

        System.out.println(sb);

    }

    public static int fn(int N, int[] coins, int M) {
        int[] dp = new int[M + 1];

        for (int i = 0; i < N; i++) {
            for (int j = coins[i]; j <= M; j++) {
                dp[j] += dp[j - coins[i]];

                if (j - coins[i] == 0) {
                    dp[j]++;
                }
            }
        }

        return dp[M];
    }

}
