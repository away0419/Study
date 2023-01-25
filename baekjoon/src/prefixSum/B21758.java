package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.zip.InflaterInputStream;

/* 꿀따기
 * N : 장소 수
 * arr[] : 꿀 양 배열
 * sum[] : 누적합
 * max : 최대 꿀
 */
public class B21758 {
	public static int N, arr[];
	public static long max, sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N + 1];
		sum = new long[N + 1];
		StringTokenizer st = new StringTokenizer(br.readLine());

		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[i] = sum[i - 1] + arr[i];
		}

		// 벌1 : 맨 왼쪽, 꿀통 : 맨 오른쪽
		for (int i = 2; i < N; i++) {
			long bug1 = sum[N] - arr[1] - arr[i];
			long bug2 = sum[N] - sum[i];
			max = Math.max(max, bug1 + bug2);
		}

		// 오른쪽 : 꿀통, 맨 왼쪽 : 벌1
		for (int i = 2; i < N; i++) {
			long bug1 = sum[N - 1] - arr[i];
			long bug2 = sum[i - 1];
			max = Math.max(max, bug1 + bug2);
		}

		// 맨 오른쪽 : 벌2, 맨 왼쪽 : 벌1
		for (int i = 2; i < N; i++) {
			long bug1 = sum[i] - arr[1];
			long bug2 = sum[N - 1] - sum[i - 1];
			max = Math.max(max, bug1 + bug2);
		}

		System.out.println(max);
	}

}
