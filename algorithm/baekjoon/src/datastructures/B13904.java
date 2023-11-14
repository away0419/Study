package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B13904 {
    public static class Info implements Comparable<Info> {
        int day;
        int cost;

        public Info(int day, int cost) {
            this.day = day;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            if (this.day == o.day) {
                return o.cost - this.cost;
            }
            return o.day - this.day;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        PriorityQueue<Info> pq = new PriorityQueue<>();

        int max = 0;
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int day = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            pq.add(new Info(day, cost));
            max = Math.max(max, day);
        }

        int answer = 0;
        while (!pq.isEmpty() && max > 0) {
            PriorityQueue<Info> pq2 = new PriorityQueue<>((o1, o2) -> o2.cost - o1.cost);

            while (!pq.isEmpty() && pq.peek().day >= max) {
                Info cur = pq.poll();
                pq2.add(cur);
            }

            if (!pq2.isEmpty()) {
                Info cur = pq2.poll();
                answer += cur.cost;
            }

            pq.addAll(pq2);
            max--;
        }

        System.out.println(answer);
    }
}
