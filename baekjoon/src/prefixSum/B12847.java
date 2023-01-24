package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 꿀 아르바이트
 * 
 * N : 총 일 수
 * M : 연속 일 수
 * arr[] : 급여 배열
 * sum[] : M일간 일한 총 급여
 */
public class B12847 {
	static int N, M, arr[];
	static long sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N];
		sum = new long[N - M + 1];

		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < M; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[0] += arr[i];
		}

		int s = 1;
		for (int i = M; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[s] = sum[s - 1] + arr[i] - arr[i - M];
			s++;
		}
		Arrays.sort(sum);

		System.out.println(sum[N - M]);
	}

}
