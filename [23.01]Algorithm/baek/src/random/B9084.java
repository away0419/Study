package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B9084 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            int[] coins = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            int price = Integer.parseInt(br.readLine());
            int[] dp = new int[price + 1];

            for (int i = 0; i < N; i++) {
                coins[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < N; i++) {
                for (int j = coins[i]; j <= price; j++) {
                    int diff = j - coins[i];
                    dp[j] += dp[diff];
                    if (diff == 0) {
                        dp[j]++;
                    }
                }
            }

            sb.append(dp[price]).append("\n");

        }

        System.out.println(sb);

    }
}
