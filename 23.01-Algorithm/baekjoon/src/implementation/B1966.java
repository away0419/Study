package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
TC : 테스트 케이스
N : 주어진 갯수
M : 찾고 싶은 인덱스
Info : 우선순위, 인덱스를 가진 정보 클래스

 */
public class B1966 {
    static int TC, N, M;

    static class Info implements Comparable<Info> {
        int index;
        int priority;

        public Info(int index, int priority) {
            this.index = index;
            this.priority = priority;
        }

        @Override
        public int compareTo(Info o) {
            return o.priority - this.priority;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        TC = Integer.parseInt(br.readLine());
        while (TC-- > 0) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
            Queue<Info> q = new ArrayDeque<>();

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                int k = Integer.parseInt(st.nextToken());
                q.add(new Info(i, k));
                pq.add(k);
            }

            int cnt = 1;
            while (!pq.isEmpty()) {
                int cur = pq.poll();

                while (!q.isEmpty()) {
                    Info info = q.poll();

                    if (info.priority != cur) {
                        q.add(info);
                    } else {
                        if (info.index == M) {
                            sb.append(cnt).append("\n");
                        }
                        break;
                    }
                }
                cnt++;
            }
        }

        System.out.println(sb);
    }
}
