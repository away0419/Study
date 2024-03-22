package retry2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B24461 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[] cnts = new int[N];
        List<Integer>[] edgeLists = new ArrayList[N];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            edgeLists[i] = new ArrayList<>();
        }

        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edgeLists[a].add(b);
            edgeLists[b].add(a);
            cnts[a]++;
            cnts[b]++;
        }


        System.out.println(sb);

    }

    public static String fn(List<Integer>[] lists, int[] levels, int N) {
        StringBuilder sb = new StringBuilder();
        boolean[] visits = new boolean[N];
        Deque<Integer> dq = search(levels, N);

        while (dq.size() > 2) {
            Deque<Integer> dq2 = new ArrayDeque<>(dq);
            dq.clear();

            while (!dq2.isEmpty()) {
                int cur = dq2.poll();
                visits[cur] = true;

                for (int next :
                        lists[cur]) {
                    if(!visits[next] && --levels[next] == 1){
                        dq.add(next);
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (!visits[i]) {
                sb.append(i).append(" ");
            }
        }

        return sb.toString();
    }

    public static Deque<Integer> search(int[] levels, int N) {
        Deque<Integer> dq = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            if (levels[i] == 1) {
                dq.offer(i);
            }
        }

        return dq;
    }


}
