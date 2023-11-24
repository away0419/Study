package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
나이트의 이동
TC : 테스트 케이스 수
N : 행렬 길이
nx, ny : 나이트 시작 지점
ex, xy : 도착 지점
 */
public class B7562 {
    static int TC, N, NX, NY, EX, EY;
    static int idx[] = {-2, -1, -2, -1, 2, 1, 2, 1};
    static int idy[] = {-1, -2, 1, 2, -1, -2, 1, 2,};

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
        TC = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (TC-- > 0) {
            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            NX = Integer.parseInt(st.nextToken());
            NY = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            EX = Integer.parseInt(st.nextToken());
            EY = Integer.parseInt(st.nextToken());

            int answer = bfs();
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }

    public static int bfs() {
        Queue<Info> q = new ArrayDeque<>();
        boolean visit[][] = new boolean[N][N];
        q.add(new Info(NX, NY, 0));
        visit[NX][NY] = true;

        while (!q.isEmpty()) {
            Info info = q.poll();

            if (info.x == EX && info.y == EY) {
                return info.leng;
            }

            for (int i = 0; i < 8; i++) {
                int sx = info.x + idx[i];
                int sy = info.y + idy[i];

                if (sx < 0 || sy < 0 || sx >= N || sy >= N || visit[sx][sy]) {
                    continue;
                }
                visit[sx][sy] = true;
                q.add(new Info(sx, sy, info.leng + 1));
            }

        }


        return -1;
    }

}
