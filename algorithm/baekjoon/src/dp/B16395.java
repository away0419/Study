package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 파스칼의 삼각형
 * N : 갯수
 * n : 행
 * k : 렬
 */
public class B16395 {
	static int N, n, k, dp[][];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken())-1;
		k = Integer.parseInt(st.nextToken())-1;
		dp = new int[31][31];
		System.out.println(fn(n, k));

	}

	public static int fn(int n, int k) {
		if (n == k || k == 0) {
			return dp[n][k] = 1;
		}
		if (dp[n][k] > 0) {
			return dp[n][k];
		}

		return dp[n][k] = fn(n - 1, k - 1) + fn(n - 1, k);
	}

}
