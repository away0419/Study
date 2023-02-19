package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 계단 오르기
 * N : 계단 수
 * arr[] : 점수
 * dp[] : 계단 별 최고 점수 저장
 */
public class B2579 {
	static int N, arr[];
	static long dp[][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N + 1];
		dp = new long[301][2];
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		dp[1][0] = arr[1];

		for (int i = 2; i <= N; i++) {
			dp[i][0] = Math.max(Math.max(dp[i][0], dp[i - 2][0] + arr[i]), dp[i - 2][1] + arr[i]);
			dp[i][1] = Math.max(dp[i - 1][0] + arr[i], dp[i][1]);

		}

		System.out.println(Math.max(dp[N][0], dp[N][1]));
	}

}
