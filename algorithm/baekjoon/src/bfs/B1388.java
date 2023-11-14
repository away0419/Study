package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 바닥 장식
 * N : 행
 * M : 열
 * answer : 바닥 장식 수
 * arr[][] = 주어진 형태
 * visit[][] = 방문 상태
 */
public class B1388 {
	static int N, M, arr[][], answer;
	static boolean visit[][];
	static int idx[] = { 1, -1, 0, 0 };
	static int idy[] = { 0, 0, 1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new int[N][M];
		visit = new boolean[N][M];
		answer = N * M;

		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				arr[i][j] = (int)str.charAt(j);
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (visit[i][j])
					continue;
				visit[i][j] = true;
				dfs(i, j);
			}
		}

		System.out.println(answer);
	}

	public static void dfs(int x, int y) {

		
		if (arr[x][y] == '-') {
			for (int i = 2; i < 3; i++) {
				int sx = idx[i] + x;
				int sy = idy[i] + y;

				if (!rangeCheck(sx, sy) || visit[sx][sy] || arr[sx][sy] != '-') {
					continue;
				}
				answer--;
				visit[sx][sy] = true;
				dfs(sx, sy);

			}

		} else if (arr[x][y] == '|') {
			for (int i = 0; i < 2; i++) {
				int sx = idx[i] + x;
				int sy = idy[i] + y;

				if (!rangeCheck(sx, sy) || visit[sx][sy] || arr[sx][sy] != '|') {
					continue;
				}
				answer--;
				visit[sx][sy] = true;
				dfs(sx, sy);

			}
		}
	}

	public static boolean rangeCheck(int x, int y) {
		if (x < 0 || y < 0 || x >= N || y >= M) {
			return false;
		}
		return true;
	}
}
