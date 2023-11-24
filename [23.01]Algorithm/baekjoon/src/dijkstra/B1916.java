package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
최소 비용 구하기
N : 도시 수
M : 버스 수
list[] : 간선 배열
visit[] : 방문
INF : 최대 합
 */
public class B1916 {
    static int N, M, dp[];
    static List<Edge> list[];
    static boolean visit[];
    static int INF = 1_000_000_000;

    static class Edge implements Comparable<Edge> {
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        list = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            list[i] = new ArrayList<>();
        }
        dp = new int[N + 1];
        Arrays.fill(dp, INF);
        visit = new boolean[N + 1];
        StringTokenizer st;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            list[from].add(new Edge(to, cost));
        }
        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        dijkstra(start, end);
        System.out.println(dp[end]);
    }

    public static void dijkstra(int start, int end) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;
            int cost = edge.cost;

            if (cur == end) {
                return;
            }
            if (visit[cur]) {
                continue;
            }
            visit[cur] = true;

            for (Edge next :
                    list[cur]) {
                int sum = cost + next.cost;
                if (dp[next.to] > sum) {
                    dp[next.to] = sum;
                    pq.add(new Edge(next.to, sum));
                }
            }


        }
    }
}
