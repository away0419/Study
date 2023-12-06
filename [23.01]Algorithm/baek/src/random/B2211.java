package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B2211 {

    public static class Info implements Comparable<Info>{
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

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Info>[] lists = new ArrayList[N+1];
        for (int i = 0; i < N + 1; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            lists[a].add(new Info(b,c));
            lists[b].add(new Info(a,c));
        }
        StringBuilder sb = new StringBuilder();
        fn(N, lists, sb);

        System.out.println(sb);
    }

    public static void fn(int N, List<Info>[] lists, StringBuilder sb){
        PriorityQueue<Info> pq = new PriorityQueue<>();
        int[] dist = new int[N+1];
        Map<Integer,Integer> map  = new HashMap<>();
        Arrays.fill(dist, 1000000);
        pq.add(new Info(1,0));
        dist[1] = 0;


        while(!pq.isEmpty()){
            Info info = pq.poll();

            if(info.cost > dist[info.no]) continue;

            for (Info next :
                    lists[info.no]) {
                int sum = info.cost + next.cost;
                if(dist[next.no] > sum){
                    dist[next.no] = sum;
                    pq.add(new Info(next.no, sum));
                    map.put(next.no, info.no);
                }
            }
        }

        sb.append(N-1).append("\n");
        for (Integer key :
                map.keySet()) {
            sb.append(key).append(" ").append(map.get(key)).append("\n");
        }
    }
}
