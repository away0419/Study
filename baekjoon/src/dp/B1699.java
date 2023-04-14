package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
제곱의수
N : 주어진 수

 */
public class B1699 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int dp[] = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            dp[i] = 1;
            for (int j = 1; j * j <= i; j++) {
                int k = dp[i - j * j] + 1;
                if (dp[i] > k) {
                    dp[i] = k;
                }
            }
        }
        System.out.println(dp[N]);
    }


}
