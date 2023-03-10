package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
list[] : 배열 리스트
V : 집
E : 다리
visit[] : 방문 여부
min : 최소
 */
public class B13905 {
    static int V, E, S, R, min;
    static boolean[] visit;
    static List<Edge>[] list;

    static class Edge implements Comparable<Edge> {
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }


        @Override
        public int compareTo(Edge o) {
            return o.cost - this.cost;
        }
    }

    public static void prim() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(S - 1, Integer.MAX_VALUE));
        int cnt = 0;
        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int cur = e.to;
            int cost = e.cost;
            if (visit[cur]) {
                continue;
            }

            min = Math.min(min, cost);
            visit[cur] = true;
            if (cur == R - 1) {
                return;
            }
            if (++cnt == V) {
                return;
            }
            pq.addAll(list[cur]);

        }
        if (cnt != V) {
            min = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        list = new ArrayList[V];
        visit = new boolean[V];
        min = Integer.MAX_VALUE;
        for (int i = 0; i < V; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());

            list[from].add(new Edge(to, cost));
            list[to].add(new Edge(from, cost));
        }

        prim();
        System.out.println(min);
    }
}
