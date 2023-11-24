package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
전기가 부족해
N : 도시
M : 케이블
K : 발전소 수
powerPlants : 발전소 번호, set
arr[] : info 배열

 */
public class B10423 {
    static int N, M, K, parent[];
    static Info[] infos;

    public static class Info implements Comparable<Info> {
        int from;
        int to;
        int cost;

        @Override
        public int compareTo(Info o) {
            return this.cost - o.cost;
        }

        public Info(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        parent = new int[N + 1];
        infos = new Info[M + K];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N+1; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < K; i++) {
            int s = Integer.parseInt(st.nextToken()) ;
            infos[M + i] = new Info(0, s, 0);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) ;
            int to = Integer.parseInt(st.nextToken()) ;
            int cost = Integer.parseInt(st.nextToken());
            infos[i] = new Info(from, to, cost);
        }

        Arrays.sort(infos);

        int answer = 0;
        int cnt = 0;
        for (int i = 0; i < M+K; i++) {
            int from = infos[i].from;
            int to = infos[i].to;
            int cost = infos[i].cost;

            if (find(from) != find(to)) {
                union(from, to);
                answer += cost;
                if (N  == ++cnt) {
                    break;
                }
            }
        }
        System.out.println(answer);
    }

    public static int find(int value) {
        if (parent[value] == value) {
            return value;
        }
        return find(parent[value]);
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a < b) {
            parent[a] = b;
        } else {
            parent[b] = a;
        }
    }

}
