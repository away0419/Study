package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 퇴사
 * N : 퇴사하는날
 */
public class B14501 {
	static int N, period[], pay[], dp[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		period = new int[N];
		pay = new int[N];
		dp = new int[N + 1];

		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			period[i] = Integer.parseInt(st.nextToken());
			pay[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < N; i++) {
			if (i + period[i] <= N )
				dp[i + period[i]] = Math.max(dp[i] + pay[i], dp[i + period[i]]);
			dp[i+1] = Math.max(dp[i+1], dp[i]);
		}
		

		System.out.println(dp[N]);
	}

}
