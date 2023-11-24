package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 투자의 귀재 배주형
 * H : 초기 비용
 * Y : 지난 년도
 * dp[] : 년도별 금액
 * arr[] : 이율
 * 
 */
public class B19947 {
	static int H, Y, dp[];
	static double arr[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		Y = Integer.parseInt(st.nextToken());
		arr = new double[] { 1, 1.05, 1, 1.2, 1, 1.35 };
		dp = new int[Y + 1];
		dp[0] = H;
		if (Y >= 3) {
			dp[3] = (int) (dp[0] * arr[3]);
		}
		if (Y >= 5) {
			dp[5] = (int) (dp[0] * arr[5]);
		}

		for (int i = 1; i <= Y; i++) {
			dp[i] = (int) Math.max((dp[i - 1] * arr[1]), dp[i]);
			if (i <= Y - 3) {
				dp[i + 3] = (int) Math.max(dp[i + 3], dp[i] * arr[3]);
			}
			if (i <= Y - 5) {
				dp[i + 5] = (int) Math.max(dp[i + 5], dp[i] * arr[5]);
			}
		}

		System.out.println(dp[Y]);
	}

}
