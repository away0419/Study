package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 스타트와 링크
 *  N: 총 사람 수, 짝수
 *  arr[][] : 점수
 *  min : 최솟값
 *  visit[] : 방문
 */
public class B14889 {
	static int N, arr[][], min;
	static boolean visit[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		visit = new boolean[N];
		min = Integer.MAX_VALUE;
		StringTokenizer st = null;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		fn(0, 0);
		System.out.println(min);

	}

	public static void fn(int cur, int cnt) {
		if (cnt == N / 2) {
			int res = diff();
			min = Math.min(min, res);
			return;
		}

		for (int i = cur; i < N; i++) {
			visit[i] = true;
			fn(i + 1, cnt + 1);
			visit[i] = false;
		}
	}

	public static int diff() {
		int res1 = 0;
		int res2 = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (visit[j] && visit[i]) {
					res1 += arr[i][j] + arr[j][i];
				} else if (!visit[j] && !visit[i]) {
					res2 += arr[i][j] + arr[j][i];
				}
			}
		}

		return Math.abs(res1 - res2);
	}

}
