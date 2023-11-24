package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 치즈
public class B2638 {
	static int N, M, answer, cCnt;
	static int map[][];
	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };

	public static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				int c = Integer.parseInt(st.nextToken());
				map[i][j] = c;
				if (c == 1) {
					cCnt++;
				}
			}
		}

		outAirInit();
		
		while (cCnt > 0) {
			answer++;
			deleteCheese();
		}

		System.out.println(answer);

	}

	// 범위를 벗어 났는지 판별
	public static boolean rangeCheck(int x, int y) {
		if (x >= 0 && y >= 0 && x < N && y < M)
			return true;

		return false;
	}

	// 외부 공기 초기화
	public static void outAirInit() {
		Queue<Position> q = new LinkedList<>();
		boolean visit[][] = new boolean[N][M];
		q.add(new Position(0, 0));
		visit[0][0] = true;
		map[0][0] = 2;

		while (!q.isEmpty()) {
			Position p = q.poll();

			for (int i = 0; i < 4; i++) {
				int sx = p.x + dx[i];
				int sy = p.y + dy[i];

				if (!rangeCheck(sx, sy) || visit[sx][sy])
					continue;
				visit[sx][sy] = true;
				if (map[sx][sy] != 1) {
					map[sx][sy] = 2;
					q.add(new Position(sx, sy));
				}
			}
		}
	}

	//치즈 삭제
	public static void deleteCheese() {
		Queue<Position> q = new LinkedList<>();
		boolean[][] visit = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 1) {
					int cnt = 0;
					for (int k = 0; k < 4; k++) {
						int sx = i + dx[k];
						int sy = j + dy[k];
						if (map[sx][sy] == 2) {
							cnt++;
						}
					}
					if (cnt > 1) {
						q.add(new Position(i, j));
						visit[i][j] = true;
					}
				}
			}
		}
		
		cCnt-=q.size();
		
		while(!q.isEmpty()) {
			Position p = q.poll();
			
			if(map[p.x][p.y]==1)
				map[p.x][p.y]=2;
			
			for (int i = 0; i < 4; i++) {
				int sx = p.x + dx[i];
				int sy = p.y + dy[i];

				if (!rangeCheck(sx, sy) || visit[sx][sy])
					continue;
				
				visit[sx][sy] = true;
				if (map[sx][sy] == 0) {
					map[sx][sy] = 2;
					q.add(new Position(sx, sy));
				}
			}
		}
	}

}

/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int[][] map;
    private static boolean[][] visited;
    private static ArrayDeque<int[]> air = new ArrayDeque<>();
    private static final int[] dx = { 1, -1, 0, 0 };
    private static final int[] dy = { 0, 0, 1, -1 };
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j * 2) - 48;
            }
        }
        air.add(new int[] { 0, 0 });

        int time = 0;
        while (true) {
            int size = air.size();

            while (size-- > 0) {
                int[] tmp = air.poll();
                dfs(tmp[0], tmp[1]);
            }

            if (air.isEmpty()) {
                break;
            }
            time++;
        }
        System.out.println(time);
    }

    private static void dfs(int x, int y) {
        visited[y][x] = true;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if ((nx >= 0 && nx < M && ny >= 0 && ny < N) && !visited[ny][nx]) {
                if (map[ny][nx] != 0) {
                    if (++map[ny][nx] == 3) {
                        air.add(new int[]{nx, ny});
                    }
                } else {
                    dfs(nx, ny);
                }
            }
        }
    }
}
*/