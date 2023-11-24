package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
배열 탈출
N : 주어진 수
table[][] : 주어진 배열

 */
public class B11909 {
    static int N;
    static int INF = 1000000000;
    static int[][] table, dp;

    public static class Info implements Comparable<Info> {
        int x;
        int y;
        int cost;

        public Info(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        table = new int[N][N];
        dp = new int[N][N];
        StringTokenizer st;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                table[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = INF;
            }
        }

        int answer = 0;
        int[] idx = {0, 1};
        int[] idy = {1, 0};
        PriorityQueue<Info> pq = new PriorityQueue<>();
        boolean[][] visit = new boolean[N][N];
        pq.add(new Info(0, 0, 0));
        dp[0][0] = 0;

        while (!pq.isEmpty()) {
            Info info = pq.poll();

            if (info.x == N - 1 && info.y == N - 1) {
                answer = info.cost;
                break;
            }
            if (visit[info.x][info.y]) {
                continue;
            }
            visit[info.x][info.y] = true;

            for (int i = 0; i < 2; i++) {
                int sx = idx[i] + info.x;
                int sy = idy[i] + info.y;

                if (sx >= N || sy >= N) {
                    continue;
                }
                int cnt = table[info.x][info.y] <= table[sx][sy] ? table[sx][sy] - table[info.x][info.y] + 1 : 0;
                if (dp[sx][sy] > dp[info.x][info.y] + cnt) {
                    dp[sx][sy] = dp[info.x][info.y] + cnt;
                    pq.add(new Info(sx, sy, dp[sx][sy]));
                }
            }
        }

        System.out.println(answer);

    }

}
