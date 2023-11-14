package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B12887 {
    static Queue<Info> q;
    static boolean[][] visits;
    static int white;

    public static class Info {
        int x;
        int y;
        int leng;

        public Info(int x, int y, int leng) {
            this.x = x;
            this.y = y;
            this.leng = leng;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = 2;
        int m = Integer.parseInt(br.readLine());
        int[][] arr = new int[n][m];
        white = 0;
        visits = new boolean[n][m];
        q = new ArrayDeque<>();

        for (int i = 0; i < 2; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                arr[i][j] = str.charAt(j);
                if (arr[i][j] == '.') {
                    white++;
                }
                if (j == 0 && arr[i][j] == '.') {
                    q.add(new Info(i, j, 1));
                    visits[i][j] = true;
                }
            }
        }

        bfs(n, m, arr);

        System.out.println(white);

    }

    public static void bfs(int n, int m, int[][] arr) {
        int[] idx = {0, 0, -1, 1};
        int[] idy = {-1, 1, 0, 0};
        while (!q.isEmpty()) {
            Info info = q.poll();


            if (info.y == m - 1) {
                white -= info.leng;
                return;
            }

            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + info.x;
                int sy = idy[i] + info.y;

                if (sx >= n || sx < 0 || sy >= m || sy < 0 || arr[sx][sy] == '#' || visits[sx][sy]) {
                    continue;
                }

                visits[sx][sy] = true;
                q.add(new Info(sx, sy, info.leng + 1));

            }
        }
    }
}
