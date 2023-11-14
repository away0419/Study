package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
암호길에 다리 놓기
N : 강의장 수
M : 공사장 구간의 수
K : 가진 돌 수
sum : 현재까지의 돌 수
arr[] : 강의장 연결
list[] : 리스트 배열
 */
public class B17490 {
    static int N, M, K;
    static long sum;
    static boolean arr[];
    static List<Edge> list[];
    static String answer;

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
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        list = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            list[i] = new ArrayList<>();
        }

        arr = new boolean[N + 1];
        answer = "YES";
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < N + 1; i++) {
            int k = Integer.parseInt(st.nextToken());
            list[i].add(new Edge(0, k));
            list[0].add(new Edge(i, k));
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            arr[s] = true;

        }

        for (int i = 1; i < N; i++) {
            if (!arr[i]) {
                list[i].add(new Edge(i + 1, 0));
                list[i + 1].add(new Edge(i, 0));
            }
        }

        if (!arr[N]) {
            list[N].add(new Edge(1, 0));
            list[1].add(new Edge(N, 0));
        }

        prim();
        System.out.println(answer);

    }


    public static void prim() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, 0));
        boolean visit[] = new boolean[N + 1];
        int cnt = 0;
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;
            int cost = edge.cost;

            if (visit[cur]) {
                continue;
            }

            visit[cur] = true;
            sum += cost;

            if (sum > K) {
                answer = "NO";
                return;
            }

            if (++cnt == N + 1) {
                return;
            }

            for (Edge next :
                    list[cur]) {
                if (!visit[next.to]) {
                    pq.add(next);
                }
            }

        }
    }
}