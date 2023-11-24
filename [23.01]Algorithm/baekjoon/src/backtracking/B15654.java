package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * N과 M (5)
 * N : 배열 수
 * M : 조합 길이
 * arr[] : 주어진 배열
 */
public class B15654 {
	static int N, M, arr[];
	static StringBuilder sb;
	static boolean visit[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N];
		visit = new boolean[N];
		sb = new StringBuilder();
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
			if (visit[i])
				continue;
			visit[i] = true;
			fn(cnt + 1, str + arr[i] + " ");
			visit[i] = false;
		}
	}

}
