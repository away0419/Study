package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 벽부수기3
 * N : 행
 * M : 렬
 * K : 벽 부수기 가능 횟수
 * arr[][] : 맵
 * 
 */
public class B16933 {
	static int N, M, K, answer;
	static boolean arr[][], visit[][][][];
	static int[] idx = { 0, 0, -1, 1 };
	static int[] idy = { -1, 1, 0, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new boolean[N][M];
		visit = new boolean[N][M][K + 1][2];
		answer = -1;
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				if (str.charAt(j) == '1') {
					arr[i][j] = true;
				}
			}
		}
		bfs();
		System.out.println(answer);

	}

	public static void bfs() {
		Queue<Position> q = new LinkedList<Position>();
		q.add(new Position(0, 0, 0, 1, 0));
		visit[0][0][0][0] = true;

		while (!q.isEmpty()) {
			Position p = q.poll();

			if (p.x == N - 1 && p.y == M - 1) {
				answer = p.leng;
				return;
			}
			int tt = (p.t == 1) ? 0 : 1;

			for (int i = 0; i < 4; i++) {
				int sx = idx[i] + p.x;
				int sy = idy[i] + p.y;

				if (sx < 0 || sy < 0 || sx >= N || sy >= M) {
					continue;
				}

				// 현재 낮이고 벽인경우
				if (p.t == 0 && p.k < K && arr[sx][sy] && !visit[sx][sy][p.k + 1][tt]) {
					q.add(new Position(sx, sy, p.k + 1, p.leng + 1, tt));
					visit[sx][sy][p.k + 1][tt] = true;

				}
				// 벽이 아닌 경우
				else if (!arr[sx][sy] && !visit[sx][sy][p.k][tt]) {
					q.add(new Position(sx, sy, p.k, p.leng + 1, tt));
					visit[sx][sy][p.k][tt] = true;

				}
				
				// 밤이고 벽이면서 남은 벽뿌시기 경우가 있을 때 한턴 쉬기
				else if (p.t==1 && p.k < K && arr[sx][sy] && !visit[p.x][p.y][p.k][tt]) {
					q.add(new Position(p.x, p.y, p.k, p.leng + 1, tt));
					visit[p.x][p.y][p.k][tt] = true;
				}
			}


		}

	}

	public static class Position {
		int x;
		int y;
		int k;
		int leng;
		int t;

		public Position(int x, int y, int k, int leng, int t) {
			super();
			this.x = x;
			this.y = y;
			this.k = k;
			this.leng = leng;
			this.t = t;
		}

	}
}
