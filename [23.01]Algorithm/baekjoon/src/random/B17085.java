package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class B17085 {
    static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        answer = 1;

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = (int) str.charAt(j);
            }
        }

        combination(0, n, m, map, 0, new int[2][2]);

        System.out.println(answer);

    }

    public static void combination(int cur, int n, int m, int[][] map, int cnt, int[][] position) {
        if (cnt == 2) {
            boolean[][] visits = new boolean[n][m];
            int i = 0;

            while (next(n, m, map, position[0][0], position[0][1], i, visits)) {
                int j = 0;
                while (next(n, m, map, position[1][0], position[1][1], j, visits)) {
                    answer = Math.max(answer, (i * 4 + 1) * (j * 4 + 1));
                    prev(position[1][0], position[1][1], j, visits);
                    j++;
                }
                i++;
            }
            return;
        }

        for (int k = cur; k < n * m; k++) {
            int x = k / m;
            int y = k % m;
            if (map[x][y] == '#') {
                position[cnt][0] = x;
                position[cnt][1] = y;
                combination(k + 1, n, m, map, cnt + 1, position);
            }
        }

    }

    public static boolean next(int n, int m, int[][] map, int x, int y, int cnt, boolean[][] visits) {
        int a = x + cnt;
        int b = x - cnt;
        int c = y + cnt;
        int d = y - cnt;

        if (a >= n || b < 0 || c >= m || d < 0 || map[a][y] == '.' || map[b][y] == '.' || map[x][c] == '.' || map[x][d] == '.' || visits[a][y] || visits[b][y] || visits[x][c] || visits[x][d]) {
            return false;
        }

        visits[a][y] = true;
        visits[b][y] = true;
        visits[x][c] = true;
        visits[x][d] = true;


        return true;
    }

    public static void prev(int x, int y, int cnt, boolean[][] visits) {
        int a = x + cnt;
        int b = x - cnt;
        int c = y + cnt;
        int d = y - cnt;

        visits[a][y] = false;
        visits[b][y] = false;
        visits[x][c] = false;
        visits[x][d] = false;
    }
}
