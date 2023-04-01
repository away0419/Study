package dijkstra;
/*
백도어
N : 분기점
M : 길
visit[] : 방문 배열
list[] : 리스트 배열
dp[] 최소 시간
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class B17396 {
    static int N, M;
    static long dp[];
    static boolean visit[];
    static List<Edge> list[];

    static class Edge implements Comparable<Edge> {
        int to;
        long cost;

        public Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.cost,o.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        visit = new boolean[N];
        list = new ArrayList[N];
        dp = new long[N];
        Arrays.fill(dp, 10_000_000_001L);
        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N-1; i++) {
            int k = Integer.parseInt(st.nextToken());
            if (k == 1) {
                visit[i] = true;
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            list[from].add(new Edge(to, cost));
            list[to].add(new Edge(from, cost));

        }

        System.out.println(dijkstra());

    }

    public static long dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, 0));
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;
            long cost = edge.cost;

            if (cur == N - 1) {
                return cost;
            }
            if (visit[cur]) {
                continue;
            }
            visit[cur] = true;

            for (Edge next :
                    list[cur]) {
                if (visit[next.to]) {
                    continue;
                }
                if (dp[next.to] > cost + next.cost) {
                    dp[next.to] = cost + next.cost;
                    pq.add(new Edge(next.to, dp[next.to]));
                }
            }

        }
        return -1;
    }
}
