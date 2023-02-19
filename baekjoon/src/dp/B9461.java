package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B9461 {
	static int T, N;
	static long dp[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		dp = new long[101];
		dp[1] = dp[2] = dp[3] = 1;
		dp[4] = dp[5] = 2;

		for (int i = 6; i <= 100; i++) {
			dp[i] = dp[i - 3] + dp[i - 2];
		}
		while (T-- > 0) {
			N = Integer.parseInt(br.readLine());
			System.out.println(dp[N]);
		}
	}

}
