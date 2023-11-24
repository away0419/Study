package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
도시 분할 계획
N : 집 갯수
M : 길 수
visit[] : 방문 집
list[] : 리스트 배열
answer : 답
max : 가장 큰 길이
 */
public class B1647 {
    public static int N, M, max;
    public static long answer;
    public static List<Edge>[] list;
    public static boolean[] visit;

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

    public static void fn() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, 0));
        int cnt = 0;
        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int cur = e.to;
            int cost = e.cost;

            if (visit[cur]) {
                continue;
            }

            visit[cur] = true;
            answer += cost;
            max = Math.max(max, cost);

            if (++cnt == N) {
                return;
            }

            pq.addAll(list[cur]);

        }
    }


    public static void main(String[] args) throws IOException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N];
        visit = new boolean[N];

        for (int i = 0; i < list.length; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());

            list[from].add(new Edge(to, cost));
            list[to].add(new Edge(from, cost));

        }

        fn();
        System.out.println(answer - max);


    }
}
