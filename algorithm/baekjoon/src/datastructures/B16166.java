package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
서울의 지하철
N : 지하철 갯수
K : 호선의 역 갯수

 */
public class B16166 {
    static int N, END;
    static Set<Integer> stationSets[];
    static Map<Integer, Set<Integer>> stationMap;

    public static class Info {
        int subway;
        int leng;

        public Info(int subway, int leng) {
            this.subway = subway;
            this.leng = leng;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        stationSets = new HashSet[N];
        stationMap = new HashMap<>();
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            stationSets[i] = new HashSet<>();
            int k = Integer.parseInt(st.nextToken());
            for (int j = 0; j < k; j++) {
                int x = Integer.parseInt(st.nextToken());
                stationSets[i].add(x);

                Set<Integer> set = stationMap.getOrDefault(x, new HashSet<Integer>());
                set.add(i);
                stationMap.put(x, set);
            }
        }

        END = Integer.parseInt(br.readLine());
        int res = fn();
        System.out.println(res);

    }

    public static int fn() {
        Queue<Info> q = new ArrayDeque<>();
        Set<Integer> set = stationMap.get(END);
        boolean visit[] = new boolean[N];
        for (int i :
                set) {
            q.add(new Info(i, 0));
            visit[i] = true;
        }

        while (!q.isEmpty()) {
            Info info = q.poll();
            int cur = info.subway;

            if (stationSets[cur].contains(0)) {
                return info.leng;
            }

            for (int next :
                    stationSets[cur]) {
                for (int nextSub :
                        stationMap.get(next)) {
                    if (visit[nextSub]) {
                        continue;
                    }
                    visit[nextSub] = true;
                    q.add(new Info(nextSub, info.leng + 1));
                }
            }
        }
        return -1;
    }

}
