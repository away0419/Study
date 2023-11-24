package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
N : 지름길 수
D : 고속도로 길이
List[] : 리스트 배열
dp[] : 최소 길이
 */
public class B1446 {
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

    static int N, D, dp[];
    static List<Edge>[] list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        dp = new int[D + 1];
        list = new ArrayList[D + 1];
        for (int i = 0; i < D + 1; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < D + 1; i++) {
            dp[i] = i;
        }

        for (int i = 0; i < D+1; i++) {
            for (int j = i; j < D+1; j++) {
                list[i].add(new Edge(j,j-i));
            }
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            if (!(from < D + 1 && to < D + 1)) {
                continue;
            }
            list[from].add(new Edge(to, cost));
        }

        dijkstra();
        System.out.println(dp[D]);

    }

    public static void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, 0));
        boolean visit[] = new boolean[D + 1];
        dp[0] =0;
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;

            if (cur == D) {
                return;
            }
            if (visit[cur]) {
                continue;
            }
            visit[cur] = true;

            for (Edge next :
                    list[cur]) {
                if (dp[next.to] >= dp[cur] + next.cost) {
                    dp[next.to] = dp[cur] + next.cost;
                    pq.add(new Edge(next.to, dp[next.to]));
                }
            }
        }
    }
}
