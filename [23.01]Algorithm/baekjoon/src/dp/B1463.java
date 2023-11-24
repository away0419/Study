package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 1로 만들기
 * N : 주어진 정수
 */
public class B1463 {
	static int N, dp[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dp = new int[N + 1];
		for (int i = 2; i <= N; i++) {
			if(i%6==0) {
				dp[i] = Math.min(dp[i - 1] + 1, Math.min(dp[i / 2] + 1, dp[i / 3] + 1));
			}else if (i % 3 == 0) {
				dp[i] = Math.min(dp[i - 1] + 1, dp[i / 3] + 1);
			} else if (i % 2 == 0) {
				dp[i] = Math.min(dp[i - 1] + 1, dp[i / 2] + 1);
			} else {
				dp[i] = dp[i - 1] + 1;
			}
		}
		System.out.println(dp[N]);
	}

}
