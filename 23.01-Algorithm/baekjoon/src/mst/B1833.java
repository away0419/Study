package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
고속철도 설계하기
N : 도시 수
map[][] : 도로비용

 */
public class B1833 {
    static int N, map[][], parent[], cnt, min, minCnt;
    static Info[] infoArr;
    static StringBuilder sb;

    public static class Info implements Comparable<Info> {
        int from;
        int to;
        int cost;

        public Info(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            return this.cost - o.cost;
        }
    }


    static int find(int value) {
        if (parent[value] == value) {
            return value;
        }
        return find(parent[value]);
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a < b) {
            parent[a] = b;
        } else {
            parent[b] = a;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        parent = new int[N];
        infoArr = new Info[N * N];
        sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                infoArr[i * N + j] = new Info(i, j, map[i][j]);
                if(map[i][j]<0){
                    min-=map[i][j];
                }
            }
        }
        min/=2;
        Arrays.sort(infoArr);
        kruskal();
        System.out.println(min + " " + minCnt);
        System.out.println(sb);
    }

    public static void kruskal() {
        for (int i = 0; i < N * N; i++) {
            int from = infoArr[i].from;
            int to = infoArr[i].to;
            int cost = infoArr[i].cost;

            if (find(from) == find(to)) {
                continue;
            } else {
                union(from, to);
                if (cost != 0) {
                    if (cost > 0) {
                        sb.append(from + 1).append(" ").append(to + 1).append("\n");
                        minCnt++;
                        min += cost;
                    }
                    if (++cnt == N-1) {
                        return;
                    }
                }
            }


        }
    }
}
