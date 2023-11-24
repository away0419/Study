package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 점프왕 젤리
 * N : 행렬
 * arr[][] : 주어지는 맵
 */
public class B16173 {
	static int N, arr[][], N2;
	static boolean visit[][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		N2 = N * 2;
		arr = new int[N][N];
		visit = new boolean[N][N];
		
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		dfs(0, 0);
		System.out.println("Hing");

	}

	public static void dfs(int x, int y) {
		if (x == N - 1 && y == N - 1) {
			System.out.println("HaruHaru");
			System.exit(0);
		}
		int[] idx = new int[]{0,arr[x][y]};
		int[] idy = new int[]{arr[x][y],0};
		for (int i = 0; i < 2; i++) {
			int sx = x + idx[i];
			int sy = y + idy[i];

			if (!rangeCheck(sx, sy) || visit[sx][sy]) {
				continue;
			}
			visit[sx][sy] = true;
			dfs(sx, sy);

		}

	}

	public static boolean rangeCheck(int x, int y) {
		if (x < 0 || y < 0 || x >= N || y >= N) {
			return false;
		}
		return true;
	}
}
