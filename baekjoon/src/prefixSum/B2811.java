package prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 상범이의 우울
 * T :우울 기간
 * T_f : 가장 빠른 우울 기간
 * N : 총 일수
 * answer : 최대 꽃 개수
 * arr1[] : 우울 기간
 * arr2[] :	우울기간
 * check[] : 꽃 유무
 */
public class B2811 {
	static int N, answer, arr[], sum[];
	static boolean check[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		check = new boolean[N];
		arr = new int[N];
		sum = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int sums = 0;
		int max = 0;
		Queue<Integer> q = new LinkedList<>();
		for (int i = N - 1; i >= 0; i--) {
			if (arr[i] < 0) {
				sums++;

			} else {
				if (sums != 0) {
					sum[i + 1] = sums;

					for (int j = 0; j < sums * 2; j++) {
						if (i - j < 0)
							break;
						check[i - j] = true;
					}
				}
				if (max < sums) {
					max = sums;
					q.clear();
					q.add(i + 1);
				} else if (max == sums) {
					q.add(i + 1);
				}
				sums = 0;
			}
		}
		sum[0] = sums;

		if (max < sums) {
			max = sums;
			q.clear();
			q.add(0);
		} else if (max == sums) {
			q.add(0);
		}

		int m = 0;
		while (!q.isEmpty()) {
			int idx = q.poll();
			int g = 0;
			for (int i = 1; i <= max * 3; i++) {
				if (idx < i)
					break;
				if (!check[idx - i])
					g++;
			}
			m = Math.max(m, g);
		}

		for (int i = 0; i < N; i++) {
			if (check[i])
				answer++;
		}
		answer += m;
		System.out.println(answer);

	}

}
