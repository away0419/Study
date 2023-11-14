package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B23059 {
    public static class Info implements Comparable<Info> {
        int idx;
        String str;

        public Info(int idx, String str) {
            this.idx = idx;
            this.str = str;
        }

        @Override
        public int compareTo(Info o) {
            return this.str.compareTo(o.str);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        Map<String, Integer> nameIdxMap = new HashMap<>();
        Map<Integer, String> idxNameMap = new HashMap<>();
        int itemCnt = 0;
        List<List<Integer>> listlist = new ArrayList<>();
        List<Integer> cntList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            String b = st.nextToken();
            int aIdx;
            int bIdx;

            if (nameIdxMap.containsKey(a)) {
                aIdx = nameIdxMap.get(a);
            } else {
                aIdx = itemCnt++;
                nameIdxMap.put(a, aIdx);
                idxNameMap.put(aIdx, a);
                listlist.add(new ArrayList<>());
                cntList.add(0);
            }

            if (nameIdxMap.containsKey(b)) {
                bIdx = nameIdxMap.get(b);
            } else {
                bIdx = itemCnt++;
                nameIdxMap.put(b, bIdx);
                idxNameMap.put(bIdx, b);
                listlist.add(new ArrayList<>());
                cntList.add(0);
            }

            listlist.get(aIdx).add(bIdx);
            cntList.set(bIdx, cntList.get(bIdx) + 1);
        }

        PriorityQueue<Info> pq = new PriorityQueue<>();

        for (int i = 0; i < cntList.size(); i++) {
            if (cntList.get(i) == 0) {
                pq.add(new Info(i, idxNameMap.get(i)));
            }
        }

        int cnt = 0;
        while (!pq.isEmpty()) {

            Queue<Info> q = new ArrayDeque<>();
            while (!pq.isEmpty()) {
                q.add(pq.poll());
            }

            while (!q.isEmpty()) {

                Info info = q.poll();
                sb.append(info.str).append("\n");
                cnt++;
                List<Integer> edgeList = listlist.get(info.idx);

                for (int next :
                        edgeList) {
                    cntList.set(next, cntList.get(next) - 1);
                    if (cntList.get(next) == 0) {
                        pq.add(new Info(next, idxNameMap.get(next)));
                    }
                }
            }

        }
        if (cnt == cntList.size()) {
            System.out.println(sb);
        } else {

            System.out.println(-1);
        }
    }
}
