package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 안전 구역
 * N : 행과 열 갯수
 * arr[][] : 높이 배열
 * answer : 최대 영역 갯수
 * height : 현재 높이
 * visit[][] : 방문 했는지 확인
 * 
 */
public class B2468 {
	static int N, arr[][], answer, height;
	static int[] dx = { 0, 0, -1, 1 };
	static int[] dy = { 1, -1, 0, 0 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		height = 100;
		answer = 1;

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				height = Math.min(height, arr[i][j]);
			}
		}
		while (true) {
			int cnt = find();
			if (cnt == 0)
				break;
		}

		System.out.println(answer);
	}

	public static int find() {
		boolean visit[][] = new boolean[N][N];
		int cnt = 0;
		int min = 100;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr[i][j] > height && !visit[i][j]) {
					min = bfs(i, j, visit, min);
					cnt++;
				}
			}
		}
		height = min;
		answer = Math.max(cnt, answer);
		return cnt;
	}

	public static int bfs(int x, int y, boolean[][] visit, int min) {
		Queue<Position> q = new LinkedList<>();
		q.add(new Position(x, y));
		visit[x][y] = true;
		min = Math.min(min, arr[x][y]);
		while (!q.isEmpty()) {
			Position p = q.poll();

			for (int i = 0; i < 4; i++) {
				int sx = dx[i] + p.x;
				int sy = dy[i] + p.y;

				if (sx < 0 || sy < 0 || sx >= N || sy >= N || arr[sx][sy] <= height || visit[sx][sy]) {
					continue;
				}

				visit[sx][sy] = true;
				q.add(new Position(sx, sy));
				min = Math.min(min, arr[sx][sy]);

			}

		}
		return min;

	}

	public static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
