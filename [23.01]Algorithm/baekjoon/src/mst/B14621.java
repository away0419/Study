package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
나만 안되는 연애
N :학교수
M : 도로수
gender[] : 대학교 성별
answer : 최단 비용
 */
public class B14621 {
    static int N, M;
    static long answer;
    static boolean gender[];
    static List<Univer> list[];

    public static class Univer implements Comparable<Univer> {
        int to;
        int cost;

        public Univer(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Univer o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        gender = new boolean[N];
        list = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            gender[i] = "M".equals(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());

            list[from].add(new Univer(to, cost));
            list[to].add(new Univer(from, cost));
        }
        prim();
        System.out.println(answer);

    }

    public static void prim() {
        PriorityQueue<Univer> pq = new PriorityQueue<>();
        pq.add(new Univer(0, 0));
        boolean visit[] = new boolean[N];
        int cnt = 0;
        while (!pq.isEmpty()) {
            Univer u = pq.poll();
            int cur = u.to;
            int cost = u.cost;

            if (visit[cur]) {
                continue;
            }
            answer += cost;
            visit[cur] = true;

            if (++cnt == N) {
                return;
            }

            for (int i = 0; i < list[cur].size(); i++) {
                Univer next = list[cur].get(i);
                if (visit[next.to] || (gender[next.to] == gender[cur])) {
                    continue;
                }
                pq.add(next);
            }
        }
        answer = -1;
    }
}
