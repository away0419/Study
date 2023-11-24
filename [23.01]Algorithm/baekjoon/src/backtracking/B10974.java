package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 모든 순열
 * N : 총 수
 * 
 */
public class B10974 {
	static int N;
	static boolean visit[];
	static StringBuilder sb;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		sb = new StringBuilder();
		visit = new boolean[N + 1];
		fn(0, "");
		System.out.println(sb);
	}

	public static void fn(int cnt, String str) {
		if (cnt == N) {
			sb.append(str).append("\n");
			return;
		}

		for (int i = 1; i <= N; i++) {
			if (visit[i])
				continue;
			visit[i] = true;
			fn(cnt + 1, str + i + " ");
			visit[i] = false;
		}
	}
}
