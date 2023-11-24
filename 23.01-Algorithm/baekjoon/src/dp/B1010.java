package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 다리 놓기
 * T : 테스트 케이스
 * N : 서쪽 
 * M : 동쪽
 * answer : 경우의 수
 * 
 */
public class B1010 {
	static int N, M, T, answer, dp[][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		dp = new int[30][30];

		while (T-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			fn(M,N);
			System.out.println(dp[N][M]);

		}
	}

	public static int fn(int m, int n) {
		if (dp[n][m] > 0) {
			return dp[n][m];
		}

		if (n == m || n == 0) {
			return dp[n][m] = 1;
		}

		return dp[n][m] = fn(m - 1, n - 1) + fn(m - 1, n);
	}

}
