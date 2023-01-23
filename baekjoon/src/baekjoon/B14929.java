package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 귀찮아
 * 
 * N : 배열 수
 * arr[] : 주어진 배열
 * sum[] : 누적합
 * 
 */
public class B14929 {
	static int N, arr[], sum[];
	static long answer;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		sum = new int[N + 1];
		arr = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[i] = sum[i - 1] + arr[i];
		}

		for (int i = 1; i < N; i++) {
			answer += arr[i] * (sum[N] - sum[i]);
		}

		System.out.println(answer);

	}

}
