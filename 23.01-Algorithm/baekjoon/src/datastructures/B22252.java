package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
정보 상인 호석
 */
public class B22252 {
    static int N;
    static Map<String, Integer> nameMap;
    static List<PriorityQueue<Integer>> pqList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        nameMap = new HashMap<>();
        pqList = new ArrayList<>();
        int no = 0;
        long answer = 0;

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            int cnt = Integer.parseInt(st.nextToken());

            if (type == 1) {
                if (nameMap.containsKey(name)) {
                    int idx = nameMap.get(name);
                    PriorityQueue<Integer> pq = pqList.get(idx);

                    for (int j = 0; j < cnt; j++) {
                        pq.add(Integer.parseInt(st.nextToken()));
                    }

                } else {
                    PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

                    for (int j = 0; j < cnt; j++) {
                        pq.add(Integer.parseInt(st.nextToken()));
                    }
                    pqList.add(pq);
                    nameMap.put(name, no++);
                }

            } else {
                if (nameMap.containsKey(name)) {
                    int idx = nameMap.get(name);
                    PriorityQueue<Integer> pq = pqList.get(idx);
                    while (cnt-- > 0) {
                        if (pq.isEmpty()) {
                            break;
                        }
                        answer += pq.poll();
                    }
                }
            }

        }

        System.out.println(answer);

    }
}
