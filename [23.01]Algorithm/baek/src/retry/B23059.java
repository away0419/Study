package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B23059 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        Map<Integer, String> its = new HashMap<>();
        Map<String, Integer> sti = new HashMap<>();
        int[] level = new int[N*2];
        List<Integer>[] lists = new ArrayList[N*2];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N*2; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            String b = st.nextToken();

            int aidx = sti.getOrDefault(a, sti.size());
            sti.put(a, aidx);
            its.put(aidx, a);

            int bidx = sti.getOrDefault(b, sti.size());
            sti.put(b, bidx);
            its.put(bidx, b);

            lists[aidx].add(bidx);
            level[bidx]++;
        }

        int[] copylevel = new int[sti.size()];
        System.arraycopy(level,0,copylevel,0,sti.size());

        List<Integer>[] copylists = new ArrayList[sti.size()];
        for (int i = 0; i < sti.size(); i++) {
            copylists[i] = lists[i];
        }

        boolean result = fn(sti.size(), copylevel, copylists, sti, its, sb);

        if (result) {
            System.out.println(sb);
        } else {
            System.out.println(-1);
        }
    }

    public static boolean fn(int N, int[] level, List<Integer>[] lists, Map<String, Integer> sti, Map<Integer, String> its, StringBuilder sb) {
        PriorityQueue<String> pq1 = new PriorityQueue<>();
        PriorityQueue<String> pq2 = new PriorityQueue<>();
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            if (level[i] == 0) {
                pq2.offer(its.get(i));
            }
        }

        while (!pq2.isEmpty()) {
            pq1.addAll(pq2);
            pq2.clear();
            cnt += pq1.size();

            while (!pq1.isEmpty()) {
                String str = pq1.poll();
                int cur = sti.get(str);

                sb.append(str).append("\n");

                for (int next :
                        lists[cur]) {
                    if (--level[next] == 0) {
                        pq2.offer(its.get(next));
                    }
                }
            }
        }

        if (cnt != N) {
            return false;
        }

        return true;

    }
}
