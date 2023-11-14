package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* blobyum
 * 
 * N : 애플파이 총 갯수
 * K : 연속 수
 * arr[] : 파이의 합
 * sum[] : 누적 합
 * max : 최댓값
 */

public class B24499 {
	static int N, K, arr[];
	static long sum[], max;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N + K + 1];
		sum = new long[N + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		for (int i = N + 1; i <= N + K; i++) {
			arr[i] = arr[i - N];
		}

		for (int i = 1; i <= K; i++) {
			sum[0] += arr[i];
		}

		max = sum[0];

		for (int i = K + 1; i <= N + K; i++) {
			sum[i - K] = sum[i - K - 1] + arr[i] - arr[i - K];
			max = Math.max(max, sum[i - K]);
		}
		System.out.println(max);
	}

}
