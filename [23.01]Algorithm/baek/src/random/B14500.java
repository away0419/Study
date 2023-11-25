package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14500 {
    static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        int[][] visits = new int[N][M];
        int[] sx = {0, 0, -1, 1};
        int[] sy = {-1, 1, 0, 0};

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visits[i][j]++;
                permutation(1, visits, map[i][j], i, j, sx, sy, N, M, map);
                visits[i][j]--;
            }
        }

        System.out.println(answer);
    }

    public static void permutation(int cnt, int[][] visits, int sum, int x, int y, int[] sx, int[] sy, int N, int M, int[][] map) {
        if (cnt == 4) {
            answer = Math.max(answer, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int idx = x + sx[i];
            int idy = y + sy[i];

            if (idx < 0 || idx >= N || idy < 0 || idy >= M || visits[idx][idy] >= 2) {
                continue;
            }

            if (visits[idx][idy] == 1) {
                visits[idx][idy]++;
                permutation(cnt, visits, sum, idx, idy, sx, sy, N, M, map);
                visits[idx][idy]--;
                continue;
            }

            visits[idx][idy]++;
            permutation(cnt + 1, visits, sum + map[idx][idy], idx, idy, sx, sy, N, M, map);
            visits[idx][idy]--;
        }
    }
}
