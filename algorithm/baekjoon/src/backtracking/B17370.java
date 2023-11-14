package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B17370 {
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        boolean[][] visits = new boolean[45][45];
        visits[22][22] = true;
        visits[21][22] = true;
        fn(3, 0, 21, 22, visits, N);

        System.out.println(answer);
    }

    public static void fn(int vec, int cnt, int x, int y, boolean[][] visits, int N) {
        int[] idx = {1, 1, 1, -1, -1, -1};
        int[] idy = {0, -1, 1, 0, -1, 1};
        int[][] idv = {{1, 2}, {0, 4}, {0, 5}, {4, 5}, {3, 1}, {3, 2}};

        if (cnt == N-1) {
            for (int i = 0; i < 2; i++) {
                int select = idv[vec][i];
                int sx = x + idx[select];
                int sy = y + idy[select];
                if (sx < 0 || sy < 0 || sx >= 45 || sy >= 45) {
                    continue;
                }

                if (visits[sx][sy]) {
                    answer++;
                }

            }

            return;
        }

        for (int i = 0; i < 2; i++) {
            int select = idv[vec][i];
            int sx = x + idx[select];
            int sy = y + idy[select];

            if (sx < 0 || sy < 0 || sx >= 45 || sy >= 45 || visits[sx][sy]) {
                continue;
            }

            visits[sx][sy] = true;
            fn(select, cnt + 1, sx, sy, visits, N);
            visits[sx][sy] = false;

        }

    }

}
