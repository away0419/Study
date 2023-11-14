package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 수열
 * N : 전체 날짜 수
 * K : 연속 날짜
 * arr[N] : 날짜 별 온도
 * sum[N-K+1] : 연속 날짜 별 온도 합
 * 
 *  
 */
public class B2559 {
	static int N, K, arr[], sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N];
		sum = new int[N - K + 1];
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < K; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[0] += arr[i];
		}

		int s = 1;
		for (int i = K; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[s] = sum[s - 1] + arr[i] - arr[i - K];
			s++;
		}

		Arrays.sort(sum);

		System.out.println(sum[N - K]);

	}

}
