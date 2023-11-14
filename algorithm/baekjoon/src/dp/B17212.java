package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B17212 {
	static int N, arr[];
	static long dp[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dp = new long[N + 8];
		dp[1] = dp[2] = dp[5] = dp[7] = 1;
		dp[3] = dp[4] = dp[6] = 2;
		for (int i = 8; i <= N; i++) {
			dp[i] = Math.min(dp[i - 1], Math.min(dp[i - 2], Math.min(dp[i - 5], dp[i - 7]))) + 1;
		}
		System.out.println(dp[N]);
	}

}
