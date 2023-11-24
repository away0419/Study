package backtracking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* N과 M(4)
 * N : 배열 수
 * M : 길이
 */
public class B15652 {
	static int N, M;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		fn(0, 0, "");
		System.out.println(sb);
	}

	public static void fn(int cur, int cnt, String str) {
		if (cnt == M) {
			sb.append(str).append("\n");
			return;
		}

		for (int i = cur; i < N; i++) {
			fn(i, cnt + 1, str + (i + 1) + " ");
		}
	}
}
