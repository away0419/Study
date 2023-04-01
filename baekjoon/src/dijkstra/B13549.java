package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
숨바꼭질3
N : 현재 점
K : 동생 점
X : 수빈이의 위치

 */
public class B13549 {

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
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int dp[] = new int[100001];
        Arrays.fill(dp, 1000001);
        dijkstra(N, K, dp);
        System.out.println(dp[K]);
    }

    public static void dijkstra(int start, int end, int[] dp) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));
        boolean visit[] = new boolean[100001];
        dp[start] = 0;
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

            int back = cur + 1;
            int front = cur - 1;
            int tel = cur * 2;

            if (back <= 100000 && !visit[back]) {
                if (dp[back] > cost + 1) {
                    dp[back] = cost + 1;
                    pq.add(new Edge(back, cost + 1));
                }
            }
            if (tel <= 100000 && !visit[tel]) {
                if (dp[tel] > cost) {
                    dp[tel] = cost;
                    pq.add(new Edge(tel, cost));
                }
            }
            if (front >= 0 && !visit[front]) {
                if (dp[front] > cost + 1) {
                    dp[front] = cost + 1;
                    pq.add(new Edge(front, cost + 1));
                }
            }

        }
    }
}
