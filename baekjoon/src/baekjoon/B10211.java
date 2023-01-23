package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* Maximum subarray
 * 
 * T : 테스트 케이스
 * N : 배열 크기
 * arr[] : 주어진 배열
 * 
 */
public class B10211 {
	static int T, N, arr[]; 
	static long sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		while (T-- > 0) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N + 1];
			sum = new long[N + 1];
			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int i = 1; i <= N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				sum[i] = sum[i - 1] + arr[i];
			}
			
			long max = arr[1];

			for (int i = N; i >= 1; i--) {
				for (int j = i-1; j >= 0; j--) {
					max = Math.max(max, sum[i] - sum[j]);
				}
			}

			sb.append(max).append("\n");
		}

		System.out.println(sb);

	}

}
