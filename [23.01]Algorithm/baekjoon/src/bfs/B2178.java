package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
미로 탐색
N : 행
M : 열
map[][] : 주어지는 2차원 배열


 */
public class B2178 {
    static int N, M, map[][];

    static class Info {
        int x;
        int y;
        int leng;

        public Info(int x, int y, int leng) {
            this.x = x;
            this.y = y;
            this.leng = leng;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);
            }
        }

        int answer = bfs();
        System.out.println(answer+1);
    }

    public static int bfs() {
        Queue<Info> q = new ArrayDeque<>();
        boolean visit[][] = new boolean[N][M];
        int idx[] = {0, 0, -1, 1};
        int idy[] = {-1, 1, 0, 0};
        q.add(new Info(0, 0, 0));
        visit[0][0] = true;
        while (!q.isEmpty()) {
            Info info = q.poll();

            if (info.x == N - 1 && info.y == M - 1) {
                return info.leng;
            }

            for (int i = 0; i < 4; i++) {
                int sx = info.x + idx[i];
                int sy = info.y + idy[i];

                if (sx < 0 || sy < 0 || sx >= N || sy >= M || map[sx][sy] =='0' || visit[sx][sy]) {
                    continue;
                }
                visit[sx][sy] = true;
                q.add(new Info(sx, sy, info.leng + 1));

            }
        }
        return -1;
    }
}
