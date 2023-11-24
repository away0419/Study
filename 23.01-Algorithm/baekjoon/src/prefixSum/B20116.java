package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 상자의 균형
 * N : 상자 갯수
 * L : 상자 사이즈
 * arr[] : 무게 중심 좌표
 * sum[] : 무게 중심의 누적합
 */
public class B20116 {
	static int N, L, arr[];
	static long sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		arr = new int[N + 1];
		sum = new long[N + 2];
		boolean result = false;

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i < N + 1; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		sum[N] = arr[N];
		for (int i = N-1; i > 0; i--) {
			double avg = (double)sum[i+1] / (N - i);
			if (avg >= arr[i] + L || avg <= arr[i] - L) {
				result = true;
				break;
			}
			sum[i] = sum[i + 1] + arr[i];
		}
		if (result) {
			System.out.println("unstable");
		} else {
			System.out.println("stable");
		}
		
//		long s = 922337203545646;
		long s = 922337203545646L;
		

	}

}
