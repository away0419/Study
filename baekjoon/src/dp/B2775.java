package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 부녀회장이 될테야
 * T : 테스트케이스
 * k : 층
 * n : 호
 * dp[][] : [층][호]
 */
public class B2775 {
	static int T, K, N;
	static long dp[][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		dp = new long[15][15];

		for (int i = 0; i < 15; i++) {
			dp[0][i] = i;
		}

		for (int i = 1; i < 15; i++) {
			for (int j = 1; j < 15; j++) {
				dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
			}
		}

		while (T-- > 0) {
			K = Integer.parseInt(br.readLine());
			N = Integer.parseInt(br.readLine());

			System.out.println(dp[K][N]);
		}
	}

}
