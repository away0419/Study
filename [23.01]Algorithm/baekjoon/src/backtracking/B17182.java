package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B17182 {
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][N];
        answer = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i == k || i == j || j == k) {
                        continue;
                    }
                    map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
                }
            }
        }

        permutation(N, map, K, 1, 0, 1<<K);

        System.out.println(answer);
    }


    public static void permutation(int N, int[][] map, int cur, int cnt, int sum, int visits) {
        if (cnt == N) {
            answer = Math.min(answer, sum);
            return;
        }

        for (int i = 0; i < N; i++) {
            if (((visits >> i) & 1) == 1) {
                continue;
            }
            permutation(N, map, i, cnt + 1, sum + map[cur][i], visits | 1 << i);
        }
    }
}
