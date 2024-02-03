package retry;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B9370 {

    public static class Info implements Comparable<Info> {
        int no;
        int cost;

        public Info(int no, int cost) {
            this.no = no;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            return this.cost - o.cost;
        }
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()); // 교차로 도시 수
            int m = Integer.parseInt(st.nextToken()); // 도로
            int t = Integer.parseInt(st.nextToken()); // 목적지 후보 갯수

            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1; // 예술가 출발지
            int g = Integer.parseInt(st.nextToken()) - 1; // 지나간 도로 도시
            int h = Integer.parseInt(st.nextToken()) - 1; // 지나간 도로 도시

            List<Info>[] edgeLists = new ArrayList[n]; // 도로 리스트

            for (int i = 0; i < n; i++) {
                edgeLists[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) { // 도로
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1; // 도시
                int b = Integer.parseInt(st.nextToken()) - 1; // 도시
                int d = Integer.parseInt(st.nextToken()) * 2; // 코스트

                if ((a == g && b == h) || (b == g && a == h)) {
                    d--;
                }

                edgeLists[a].add(new Info(b, d));
                edgeLists[b].add(new Info(a, d));
            }

            int[] dist = dijkstra(n, s, edgeLists);
            List<Integer> answerList = new ArrayList<>();

            for (int i = 0; i < t; i++) { // 목적지 후보
                int x = Integer.parseInt(br.readLine()); // 후보 도시
                if (dist[x - 1] % 2 == 1) {
                    answerList.add(x);
                }
            }

            Collections.sort(answerList);
            for (int cur :
                    answerList) {
                sb.append(cur).append(" ");
            }

            sb.append("\n");

        }

        System.out.println(sb);
    }

    public static int[] dijkstra(int n, int s, List<Info>[] edgeLists) {
        PriorityQueue<Info> pq = new PriorityQueue<>();
        int[] dist = new int[n];
        int INF = 100 * 50000 * 2;

        Arrays.fill(dist, INF);
        pq.add(new Info(s, 0));
        dist[s] = 0;

        while (!pq.isEmpty()) {
            Info info = pq.poll();

            if (dist[info.no] < info.cost) {
                continue;
            }

            for (Info next :
                    edgeLists[info.no]) {
                if (dist[next.no] > info.cost + next.cost) {
                    dist[next.no] = info.cost + next.cost;
                    pq.add(new Info(next.no, dist[next.no]));
                }
            }

        }

        return dist;
    }

}
