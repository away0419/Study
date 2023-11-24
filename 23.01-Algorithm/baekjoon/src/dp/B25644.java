package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 최대 상승
 * N : 총 일
 * max : 가장 많은 이득
 */
public class B25644 {
	static int N, max;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		int s = Integer.parseInt(st.nextToken());
		for (int i = 1; i < N; i++) {
			int k = Integer.parseInt(st.nextToken());
			if (k <= s) {
				s = k;
			} else {
				max = Math.max(max, k - s);
			}
		}
		System.out.println(max);
	}

}
