package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
N : 세로
M : 가로
map : 주어진 토마토 상태
visit[] : 방문 여부
answer : 최대 일 수

 */
public class B7576 {
    static int N, M, map[][], answer;
    static boolean visit[][];

    public static class Position {
        int x;
        int y;
        int leng;

        public Position(int x, int y, int leng) {
            this.x = x;
            this.y = y;
            this.leng = leng;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        bfs();
        if(!check()){
            System.out.println(-1);
        }else{
            System.out.println(answer);
        }

    }

    public static void bfs() {
        int idx[] = {0, 0, -1, 1};
        int idy[] = {-1, 1, 0, 0};
        Queue<Position> q = new ArrayDeque<>();
        insert(q);

        while (!q.isEmpty()) {
            Position p = q.poll();
            answer = Math.max(answer, p.leng);
            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + p.x;
                int sy = idy[i] + p.y;

                if (sx < 0 || sy < 0 || sx >= N || sy >= M || map[sx][sy] == -1 || visit[sx][sy]) {
                    continue;
                }

                visit[sx][sy] = true;
                q.add(new Position(sx, sy, p.leng + 1));
            }
        }

    }

    public static void insert(Queue<Position> q) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 1) {
                    q.add(new Position(i, j, 0));
                    visit[i][j] = true;
                }
            }
        }
    }

    public static boolean check() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visit[i][j] && map[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }
}
