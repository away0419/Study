package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
창영이의 퇴근
N : 격자 크기
answer : 최소 비용
map : 경사도

 */
public class B22116 {
    static int N, answer, map[][];

    public static class Position implements Comparable<Position> {
        int x;
        int y;
        int cost;

        public Position(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }


        @Override
        public int compareTo(Position o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = null;
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        mst();
        System.out.println(answer);
    }

    public static void mst() {
        PriorityQueue<Position> pq = new PriorityQueue<>();
        boolean visit[][] = new boolean[N][N];
        int idx[] = {0, 0, -1, 1};
        int idy[] = {1, -1, 0, 0};

        pq.add(new Position(0, 0, 0));


        while (!pq.isEmpty()) {
            Position p = pq.poll();
            int curx = p.x;
            int cury = p.y;
            int curcost = p.cost;
            if (visit[curx][cury]) {
                continue;
            }
            answer = Math.max(answer, curcost);
            visit[curx][cury] = true;

            if (curx == N - 1 && cury == N - 1) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                int sx = curx + idx[i];
                int sy = cury + idy[i];

                if (sx < 0 || sy < 0 || sx >= N || sy >= N || visit[sx][sy]) {
                    continue;
                }

                pq.add(new Position(sx, sy, Math.abs(map[sx][sy] - map[curx][cury])));

            }


        }
    }
}
