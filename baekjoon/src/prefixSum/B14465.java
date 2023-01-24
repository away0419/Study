package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 소가 길을 건너간 이유 5
 * 
 * N : 횡단보도 갯수
 * K : 연속한 신호등 수
 * B : 고장난 신호등 갯수
 * arr[] : 신호등 상태 배열. true = 고장
 * cnt : 현재 K의 신호등 중 중 고장난 신호등 갯수
 * 
 * */
public class B14465 {
	static int N, K, B;
	static boolean arr[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		arr = new boolean[N];

		for (int i = 0; i < B; i++) {
			int c = Integer.parseInt(br.readLine());
			arr[c - 1] = true;
		}

		int cnt = 0;
		for (int i = 0; i < K; i++) {
			if (arr[i])
				cnt++;
		}
		
		int answer = cnt;
		for (int i = K; i < N; i++) {
			if (arr[i])
				cnt++;
			if (arr[i - K])
				cnt--;

			answer = Math.min(answer, cnt);

		}

		System.out.println(answer);

	}
}
