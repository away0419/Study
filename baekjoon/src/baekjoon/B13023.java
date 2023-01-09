package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B13023 {
	static int N, M;
	static List<Integer>[] list;
	static boolean visit[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		visit = new boolean[N];
		list = new ArrayList[N];

		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			list[a].add(b);
			list[b].add(a);

		}

		for (int i = 0; i < N; i++) {
			visit[i] = true;
			dfs(1, i);
			visit[i] = false;

		}

		System.out.println(0);

	}

	// dfs 탐색
	public static void dfs(int cnt, int start) {
		if (cnt == 5) {
			System.out.print(1);
			System.exit(0);
		}
		for (int cur : list[start]) {
			if (!visit[cur]) {
				visit[cur] = true;
				dfs(cnt+1, cur);
				visit[cur] = false;
			}
		}
	}
}
