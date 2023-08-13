package level3_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B17182 {
    static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] cost = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int k = 0; k < N; k++) {
            for (int j = 0; j < N; j++) {
                for (int i = 0; i < N; i++) {
                    if (k == j || i == k || i == j) {
                        continue;
                    }
                    cost[j][i] = Math.min(cost[j][i], cost[j][k] + cost[k][i]);
                }
            }
        }

        boolean[] visits = new boolean[N];
        answer = Integer.MAX_VALUE;
        visits[K] = true;
        fn(1, K, 0, N, visits, cost);

        System.out.println(answer);

    }

    public static void fn(int cnt, int pre, int sum, int N, boolean[] visits, int[][] cost) {
        if (cnt == N) {
            answer = Math.min(sum, answer);
            return;
        }

        for (int i = 0; i < N; i++) {
            if (visits[i]) {
                continue;
            }

            visits[i] = true;
            fn(cnt + 1, i, sum + cost[pre][i], N, visits, cost);
            visits[i] = false;
        }

    }

}
