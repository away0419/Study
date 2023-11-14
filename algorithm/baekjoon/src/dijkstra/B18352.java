package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
특정 거리의 도시 찾기

N : 도시수
M : 간선 수
X : 출발
K : 최단 거리
 */
public class B18352 {
    static class Edge implements Comparable<Edge> {
        int to;
        long cost;

        public Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.cost, o.cost);
        }
    }

    static int N, M, K, X;
    static List<Edge>[] list;
    static long dp[];
    static List<Integer> answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        dp = new long[N + 1];
        list = new ArrayList[N + 1];
        answer = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) {
            list[i] = new ArrayList<>();
        }
        Arrays.fill(dp, 300001);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            list[from].add(new Edge(to, 1));
        }

        dijkstra();
        Collections.sort(answer);
        for (int i : answer
        ) {
            System.out.println(i);
        }
        if (answer.isEmpty()) {
            System.out.println(-1);
        }

    }

    public static void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean[] visit = new boolean[N + 1];
        pq.add(new Edge(X, 0));
        dp[X] = 0;
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;
            long cost = edge.cost;
            if (cost == K) {
                answer.add(cur);
            } else if (cost > K) {
                return;
            }
            if (visit[cur]) {
                continue;
            }
            visit[cur] = true;

            for (Edge next :
                    list[cur]) {
                if (dp[next.to] > next.cost + cost) {
                    dp[next.to] = next.cost + cost;
                    pq.add(new Edge(next.to, dp[next.to]));
                }
            }
        }
    }

}
