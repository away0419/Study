package dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/* 영역 구하기
 * N : 열
 * M : 행
 * K : 직사각형 갯수
 * cnt : 영역 갯수
 * list<integer> : 영역 넓이
 * visit[][] : 방문 체크
 * 
 */
public class B2583 {
	static int N, M, K, cnt, sum;
	static boolean visit[][];
	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };
	static List<Integer> list;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		visit = new boolean[N][M];
		list = new ArrayList<>();

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int ly = Integer.parseInt(st.nextToken());
			int lx = N - Integer.parseInt(st.nextToken()) - 1;
			int ry = Integer.parseInt(st.nextToken()) - 1;
			int rx = N - Integer.parseInt(st.nextToken());

			for (int j = rx; j <= lx; j++) {
				for (int k = ly; k <= ry; k++) {
					visit[j][k] = true;
				}
			}

		} 

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (!visit[i][j]) {
					sum = 1;
					dfs(i, j);
					cnt++;
					list.add(sum);
				}
			}
		}
		System.out.println(cnt);
		list.stream().sorted().forEach(x -> System.out.print(x + " "));

	}

	public static void dfs(int x, int y) {
		visit[x][y] = true;
		for (int i = 0; i < 4; i++) {
			int sx = dx[i] + x;
			int sy = dy[i] + y;

			if (sx < 0 || sy < 0 || sx >= N || sy >= M || visit[sx][sy]) {
				continue;
			}
			sum++;
			dfs(sx, sy);
		}
	}

}
