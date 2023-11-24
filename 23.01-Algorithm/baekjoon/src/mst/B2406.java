package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
안정적인 네트워크
N : 컴퓨터수
M : 간선 수
map[][] : 비용 배열
min : 연결 최소 비용
cnt : 연결 할 쌍 수
visit[][] : 방문 여부
 */
public class B2406 {
    static int N, M, cnt, minCnt, map[][];
    static long min;
    static boolean visit[][];
    static List<Edge> list[];

    static StringBuilder sb;

    static class Edge implements Comparable<Edge> {
        int to;
        int from;
        int cost;

        public Edge(int from, int to, int cost) {
            this.to = to;
            this.cost = cost;
            this.from = from;
        }

        @Override
        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N];
        visit = new boolean[N][N];
        map = new int[N][N];
        sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            visit[x][y] = true;
            visit[y][x] = true;
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (visit[i][j]) {
                    map[i][j] = 0;
                }
                list[i].add(new Edge(i, j, map[i][j]));
            }
        }
        prim();
        System.out.println(min + " " + minCnt);
        System.out.println(sb);

    }

    public static void prim() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, 1, 0));
        boolean vis[] = new boolean[N];
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;
            int cost = edge.cost;

            if (vis[cur] || cur == 0) {
                continue;
            }
            min += cost;
            vis[cur] = true;
            if (cost != 0) {
                sb.append(edge.from + 1).append(" ").append(cur + 1).append("\n");
                minCnt++;
            }
            if (++cnt == N) {
                return;
            }

            for (Edge next :
                    list[cur]) {
                if (vis[next.to]) continue;
                pq.add(next);
            }
        }
    }
}
