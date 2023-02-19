package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 피보나치 수의 확장
 */
public class B1788 {
	static int N;
	static long dp[];
	static boolean check;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		if (N < 0) {
			check = true;
			N = -N;
		}
		dp = new long[N + 2];
		dp[1] = 1;

		if (!check) {
			for (int i = 2; i <= N; i++) {
				dp[i] = (dp[i - 2] + dp[i - 1]) % 1000000000;
			}
		} else {
			for (int i = 2; i <= N; i++) {
				dp[i] = (dp[i - 2] - dp[i - 1]) % 1000000000;
			}
		}
		int k = 0;
		long answer = dp[N];
		if (answer > 0) {
			k = 1;
		} else if (answer < 0) {
			k = -1;
			answer = -answer;
		}
		System.out.println(k);
		System.out.println(answer);
	}
}
