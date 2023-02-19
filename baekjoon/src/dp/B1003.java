package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 피보나치 함수
 */
public class B1003 {
	static long T, dp[][], arr[];
	static int N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		dp = new long[41][2];
		dp[0][0] = 1;
		dp[1][1] = 1;
		arr = new long[41];
		arr[1] = 1;
		T = Long.parseLong(br.readLine());
		fn(40);
		while (T-- > 0) {
			N = Integer.parseInt(br.readLine());
			System.out.println(dp[N][0] + " " + dp[N][1]);
		}
	}

	public static long fn(int n) {
		if (arr[n] > 0) {
			return arr[n];
		}

		if (n == 0) {
			return 0;
		}

		arr[n] = fn(n - 1) + fn(n - 2);
		dp[n][0] = dp[n - 1][0] + dp[n - 2][0];
		dp[n][1] = dp[n - 1][1] + dp[n - 2][1];
		return arr[n];

	}
}
