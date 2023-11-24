package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 1. 시작 지점 랜덤.
 * 2. 높은 곳 으로만 이동 가능
 * 3. 가장 많은 쉼터
 */
public class B14699 {

    public static class Info {
        int no;
        int height;
        int length;

        public Info(int no, int length) {
            this.no = no;
            this.length = length;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Set<Integer>[] edges = new Set[N + 1];
        int[] heights = new int[N + 1];
        int[] cnts = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            edges[i] = new HashSet<>();
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (heights[a] < heights[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            edges[b].add(a);
        }

        for (int i = 1; i <= N; i++) {
            fn(i, cnts, edges);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(cnts[i]).append("\n");
        }

        System.out.println(sb);
    }

    public static int fn(int idx, int[] cnts, Set<Integer>[] edges) {
        if (cnts[idx] != 0) {
            return cnts[idx];
        }

        if (edges[idx].isEmpty()) {
            return cnts[idx] = 1;
        }

        for (int next :
                edges[idx]) {
            int res = fn(next, cnts, edges )+ 1;
            cnts[idx] = Math.max(cnts[idx], res);
        }

        return cnts[idx];
    }
}
