package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
전력난
T : 테스트케이스
M : 집
N : 길
x, y, z : 집1, 집2, 거리
answer : 최소 거리
arr[] : 간선 정보
parents[] : 부모


 */
public class B6497 {
    static int M, N, parents[];
    static Edge edges[];

    public static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
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
        StringBuilder sb = new StringBuilder();
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int answer = 0;
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            if (M == 0 && N == 0) {
                break;
            }
            parents = new int[M];
            for (int i = 0; i < M; i++) {
                parents[i] =i;
            }
            edges = new Edge[N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                edges[i] = new Edge(from, to, cost);
                answer+=cost;

            }
            Arrays.sort(edges);

            int cnt = 0;
            for (int i = 0; i < N; i++) {
                Edge edge = edges[i];
                int a = find(edge.from);
                int b = find(edge.to);
                if (a != b) {
                    answer -= edge.cost;
                    union(a, b);
                    cnt++;
                }
                if (cnt == M - 1) {
                    break;
                }
            }

            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }

    public static int find(int value) {
        if (parents[value] == value) {
            return value;
        }
        return parents[value] = find(parents[value]);
    }


    public static void union(int a, int b) {
        if (a < b) {
            parents[a] = b;
        } else {
            parents[b] = a;
        }
    }
}