package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
N : 헛간
M : 간선 수
C : 비용
list[] : 리스트 배열
dp[] : 최소 비용
 */
public class B5972 {
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

    static int N, M;
    static List<Edge> list[];
    static int[] dp;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N + 1];
        dp = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            list[i] = new ArrayList<>();
        }
        Arrays.fill(dp, 1000 * 50000 + 1);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            list[from].add(new Edge(to, cost));
            list[to].add(new Edge(from, cost));

        }

        dijkstra();
        System.out.println(dp[N]);

    }

    public static void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean visit[] = new boolean[N + 1];
        pq.add(new Edge(1, 0));
        dp[0] = 0;
        dp[1] = 0;
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;

            if (cur == N) {
                return;
            }
            if (visit[cur]) {
                continue;
            }
            visit[cur] = true;

            for (Edge next :
                    list[cur]) {
                if (dp[next.to] > dp[cur] + next.cost) {
                    dp[next.to] = dp[cur] + next.cost;
                    pq.add(new Edge(next.to, dp[next.to]));
                }
            }
        }
    }
}
