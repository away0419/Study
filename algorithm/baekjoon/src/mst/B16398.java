package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
행성 연결
N : 행성 수

 */
public class B16398 {
    static int N;
    static int[] parents;
    static List<Info> list;

    public static class Info implements Comparable<Info> {
        int from;
        int to;
        long cost;

        public Info(int from, int to, long cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            return Long.compare(this.cost,o.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        parents = new int[N];
        list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int from = i;
                int to = j;
                int cost = Integer.parseInt(st.nextToken());

                list.add(new Info(from, to, cost));
            }
        }

        Collections.sort(list);

        int cnt = 0;
        long answer = 0;
        for (int i = 0; i < list.size(); i++) {
            Info info = list.get(i);
            int from = info.from;
            int to = info.to;

            if (find(from) != find(to)) {
                union(from, to);
                answer += info.cost;
                if (++cnt == N - 1) {
                    break;
                }
            }

        }

        System.out.println(answer);
    }

    public static int find(int value) {
        if (value == parents[value]) {
            return value;
        }
        return parents[value] = find(parents[value]);
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a > b) {
            parents[a] = b;
        } else {
            parents[b] = a;
        }
    }

}
