package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* N과 M(2)
 * N : 총 수
 * M : 조합 갯수
 * 
 * */
public class B15650 {
	static int N, M;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		sb = new StringBuilder();
		fn("", 0, 0);
		System.out.println(sb);

	}

	static void fn(String str, int n, int cur) {
		if (n == M) {
			sb.append(str).append("\n");
			return;
		}
		for (int i = cur; i < N; i++) {
			fn(str + (i + 1) + " ", n + 1, i + 1);
		}
	}
}
