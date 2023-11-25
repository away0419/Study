package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B24461 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[] levels = new int[N];
        List<Integer>[] lists = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            lists[a].add(b);
            lists[b].add(a);

            levels[a]++;
            levels[b]++;
        }

        System.out.println(fn(lists, levels, N));

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
