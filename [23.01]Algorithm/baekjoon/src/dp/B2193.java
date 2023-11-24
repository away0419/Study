package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 이친수
 */
public class B2193 {
	static int N;
	static long dp[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dp = new long[N + 3];
		dp[1] = dp[2] = 1;

		for (int i = 3; i <= N; i++) {
			dp[i] = dp[i - 2] + dp[i - 1];
		}

		System.out.println(dp[N]);
	}

}
