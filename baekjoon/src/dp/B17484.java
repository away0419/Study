package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 진우의 달 여행
 * N : 행
 * M : 렬
 * arr[][] : 배열
 * dp[][] : 연료 최소 값
 */
public class B17484 {

	static int N, M, arr[][];
	static long dp[][][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		dp = new long[N][M][3];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < 3; j++) {
				dp[0][i][j] = arr[0][i];
			}
		}

		for (int i = 1; i < N; i++) {
			for (int j = 0; j < M; j++) {

				if (j - 1 > 0) {
					dp[i][j][2] =Math.min(dp[i - 1][j][1], dp[i - 1][j + 1][0]);

				} else {
					dp[i][j][2] = Math.min(dp[i - 1][j][1], dp[i - 1][j][0]);
				}

				if (j + 1 < M && j - 1 > 0) {
					dp[i][j][1] = Math.min(Math.min(dp[i - 1][j - 1][0], dp[i - 1][j - 1][2]),
							Math.min(dp[i - 1][j + 1][2], dp[i - 1][j + 1][0]));

				} else if (j + 1 < M) {
					dp[i][j][1] = Math.min(dp[i - 1][j + 1][2], dp[i - 1][j + 1][0]);
				} else {
					dp[i][j][1] = Math.min(dp[i - 1][j - 1][0], dp[i - 1][j - 1][2]);
				}

				if (j + 1 < M) {
					dp[i][j][0] = Math.min(dp[i - 1][j][1], dp[i - 1][j + 1][2]);

				} else {
					dp[i][j][0] = Math.min(dp[i - 1][j][1], dp[i - 1][j][2]);
				}

				dp[i][j][0] += arr[i][j];
				dp[i][j][1] += arr[i][j];
				dp[i][j][2] += arr[i][j];

			}
		}

		long answer = Long.MAX_VALUE;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < 3; j++) {
				answer = Math.min(answer, dp[N - 1][i][j]);
			}
		}

		System.out.println(answer);

	}

}
