package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
N : 정점
M : 간선
list[] : 리스트 배열
dp[] : 최소 비용
parent[] : 최상위 부모

 */
public class B14284 {
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

    static int N, M, S, T, dp[], parent[];
    static List<Edge> list[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N + 1];
        parent = new int[N + 1];
        dp = new int[N + 1];
        int INF = 100000 * 100 + 1;
        for (int i = 0; i < N + 1; i++) {
            list[i] = new ArrayList<>();
            parent[i] = i;
            dp[i] = INF;
        }
        String sarr[] = new String[M];
        for (int i = 0; i < M; i++) {
            sarr[i] = br.readLine();
        }

        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(sarr[i]);
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            list[from].add(new Edge(to, cost));
            list[to].add(new Edge(from, cost));
        }

        dijkstra();

        System.out.println(dp[T]);


    }

    public static void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(S, 0));
        boolean[] visit = new boolean[N + 1];
        dp[0] = dp[S] = 0;

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;
            if (cur == T) {
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
