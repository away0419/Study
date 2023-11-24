package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 이건 꼭 풀어야 해!
 * 
 * N : 수 갯수
 * Q : 테스트 케이스
 * arr[] : 오름차순 배열
 * sum[] : 누적 합
 * 
 */
public class B17390 {
	static int N, Q, arr[], sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		arr = new int[N + 1];
		sum = new int[N + 1];
		st = new StringTokenizer(br.readLine());

		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);
		for (int i = 1; i <= N; i++) {
			sum[i] = sum[i - 1] + arr[i];
		}

		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());

			sb.append(sum[e] - sum[s - 1]).append("\n");
		}

		System.out.println(sb);
	}

}
