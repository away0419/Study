package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * APC는 왜 서브태스크 대회가 되었을까?
 * N : 문제 수
 * L : 역량
 * K : 최대 갯수
 * sub1 : 쉬운
 * sub2 : 어려운
 * answer : 최대 점수
 * arr[] : 난이도 배열 
 */
public class B17224 {
	static int N, L, K, answer, arr[][];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N][2];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr, (o1, o2) -> {
			if (o1[1] == o2[1]) {
				return o1[0] - o2[0];
			}
			return o1[1] - o2[1];
		});

		int cnt = 0;
		if (K != 0) {

			for (int i = 0; i < N; i++) {
				if (arr[i][0] <= L) {
					answer += 100;
					cnt++;
					if (arr[i][1] <= L) {
						answer += 40;
					}
				}
				if (cnt == K) {
					break;
				}
			}
		}

		System.out.println(answer);

	}

}
