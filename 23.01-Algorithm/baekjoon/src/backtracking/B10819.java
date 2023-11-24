package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 차이를 최대로
 * N: 총 갯수
 * arr[] : 배열
 */
public class B10819 {
	static int N, arr[], answer;
	static boolean visit[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		visit = new boolean[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < N; i++) {
			visit[i] = true;
			fn(1, arr[i], 0);
			visit[i] = false;
		}

		System.out.println(answer);
	}

	public static void fn(int cnt, int pre, int sum) {
		if (cnt == N) {
			answer = Math.max(answer, sum);
			return;
		}

		for (int i = 0; i < N; i++) {
			if (visit[i]) {
				continue;
			}
			visit[i] = true;
			fn(cnt + 1, arr[i], sum + Math.abs(pre - arr[i]));
			visit[i] = false;
		}
	}

}
