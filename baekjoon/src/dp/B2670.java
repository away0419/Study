package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 연속부분최대곱
 * N : 실수 갯수
 * arr[] : 주어진 실수 배열
 * dp[] : 연속 실수 합 중 제일 큰 값
 * max : 최댓값
 */
public class B2670 {
	static int N;
	static double arr[], dp[], max;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dp = new double[N];
		arr = new double[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Double.parseDouble(br.readLine());
		}
		dp[0] = arr[0];
		max = dp[0];
		for (int i = 1; i < N; i++) {
			dp[i] = Math.max(dp[i - 1] * arr[i], arr[i]);
			max = Math.max(dp[i], max);
		}
		System.out.printf("%.3f",max);
	}

}
