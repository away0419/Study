package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
학교 탐방하기
N : 물건 갯수
M : 도로 갯수
max : 최대
min : 최소
list[] : 리스트 배열

 */
public class B13418 {
    static int N, M, min, max;
    static List<Edge> list[];
    static boolean visit[][];

    public static class Edge implements Comparable<Edge> {
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
        visit = new boolean[N + 1][2];
        list = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < M+1; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            list[from].add(new Edge(to, cost));
            list[to].add(new Edge(from, cost));
        }
        prim(0);
        prim(1);

        System.out.println(max * max - min * min);
    }


    public static void prim(int type) {
        PriorityQueue<Edge> pq = null;
        if (type == 0) {
            pq = new PriorityQueue<>();
        } else {
            pq = new PriorityQueue<>((o1, o2) -> {
                return o2.cost - o1.cost;
            });

        }
        int cnt = 0;
        pq.add(list[0].get(0));
        visit[0][type] = true;

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int cur = edge.to;
            int cost = edge.cost;

            if (visit[cur][type]) {
                continue;
            }
            if (type == 0) {
                max += (cost == 1) ? 0 : 1;
            } else {
                min += (cost == 1) ? 0 : 1;
            }
            visit[cur][type] = true;

            if (++cnt == N) return;


            for (Edge next :
                    list[cur]) {
                if (visit[next.to][type]) continue;
                pq.add(next);
            }

        }

    }
}
