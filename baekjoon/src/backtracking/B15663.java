package backtracking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/* N과 M (9)
 * N : 전체 수
 * M : 조합 길이
 * arr[] : 배열
 * 
 */
public class B15663 {
	static int N, M, arr[];
	static boolean visit[];
	static StringBuilder sb;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		sb = new StringBuilder();
		arr = new int[N];
		visit = new boolean[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		fn(0, "");
		System.out.println(sb);

	}

	public static void fn(int cnt, String str) {
		if (cnt == M) {
			sb.append(str).append("\n");
			return;
		}
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < N; i++) {
			if (visit[i] || !set.add(arr[i]))
				continue;
			visit[i] = true;
			fn(cnt + 1, str + arr[i] + " ");
			visit[i] = false;
		}
	}

}
