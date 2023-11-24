package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
데스 나이트

 */
public class B16948 {
    static int N;

    public static class Info {
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
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int sx = Integer.parseInt(st.nextToken());
        int sy = Integer.parseInt(st.nextToken());
        int ex = Integer.parseInt(st.nextToken());
        int ey = Integer.parseInt(st.nextToken());
        int res = bfs(sx, sy, ex, ey);
        System.out.println(res);
    }

    public static int bfs(int sx, int sy, int ex, int ey) {
        Queue<Info> q = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        int[] idx = {-2, -2, 0, 0, 2, 2};
        int[] idy = {-1, 1, -2, 2, -1, 1};

        q.add(new Info(sx, sy, 0));
        visit[sx][sy] = true;


        while (!q.isEmpty()) {
            Info info = q.poll();

            if (info.x == ex && info.y == ey) {
                return info.leng;
            }

            for (int i = 0; i < 6; i++) {
                int stx = idx[i] + info.x;
                int sty = idy[i] + info.y;

                if (stx < 0 || sty < 0 || stx >= N || sty >= N || visit[stx][sty]) {
                    continue;
                }
                visit[stx][sty] = true;
                q.add(new Info(stx, sty, info.leng + 1));
            }
        }
        return -1;
    }


}
