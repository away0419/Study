package random;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class B24461 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        List<Integer>[] matrix = new ArrayList[N];
        int[] level = new int[N];
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        boolean[] check = new boolean[N];

        for (int i = 0; i < N; i++) {
            matrix[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            matrix[a].add(b);
            matrix[b].add(a);
            level[a]++;
            level[b]++;
        }

        for (int i = 0; i < N; i++) {
            if (matrix[i].size() == 1) {
                q2.add(i);
            }
        }

        while (true) {
            if (q2.size() <= 2) {
                break;
            }

            q1.addAll(q2);
            q2.clear();

            while (!q1.isEmpty()) {
                int cur = q1.poll();
                check[cur] = true;

                for (int next : matrix[cur]) {
                    if (check[next]) {
                        continue;
                    }
                    level[next]--;
                    if (level[next] == 1) {
                        q2.add(next);
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (!check[i]) {
                sb.append(i).append(" ");
            }
        }

        System.out.print(sb);
    }
}
