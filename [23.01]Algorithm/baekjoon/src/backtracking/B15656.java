package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* N과 M (7)
 * N : 전체 수
 * M : 조합 길이
 * arr[] : 배열
 * 
 */
public class B15656 {
	static int N, M, arr[];
	static StringBuilder sb;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		sb = new StringBuilder();
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		fn(0,"");
		System.out.println(sb);
	}

	public static void fn(int cnt, String str) {
		if (cnt == M) {
			sb.append(str).append("\n");
			return;
		}

		for (int i = 0; i < N; i++) {
			fn(cnt + 1, str + arr[i] + " ");
		}
	}

}
