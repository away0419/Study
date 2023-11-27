package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class B23288 {
    static int answer;
    static int cnt;

    public static class Info {
        int x;
        int y;
        int vec;

        public Info(int x, int y, int vec) {
            this.x = x;
            this.y = y;
            this.vec = vec;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] idx = {0, 0, 1, -1};
        int[] idy = {1, -1, 0, 0};
        int[][] dice = new int[4][3];
        int[][] map = new int[N][M];

        dice[0][1] = 2;
        dice[1][0] = 4;
        dice[1][1] = 1;
        dice[1][2] = 3;
        dice[2][1] = 5;
        dice[3][1] = 6;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Info info = new Info(0,0, 0);
        for (int i = 0; i < K; i++) {
            info = move(info.x, info.y, info.vec, idx, idy, dice, N, M, map);
            boolean[][] visits = new boolean[N][M];
            visits[info.x][info.y] = true;
            bfs(info.x, info.y, idx, idy, visits, N, M, map);
            answer += cnt * map[info.x][info.y];
            cnt = 0;
        }

        System.out.println(answer);

    }

    public static boolean check(int vec, int x, int y, int[] idx, int[] idy, int N, int M) {
        int sx = x + idx[vec];
        int sy = y + idy[vec];

        if (sx < 0 || sy < 0 || sx >= N || sy >= M) {
            return false;
        }

        return true;
    }

    public static Info move(int x, int y, int vec, int[] idx, int[] idy, int[][] dice, int N, int M, int[][] map) {

        if (!check(vec, x, y, idx, idy, N, M)) {
            if (vec == 0) {
                vec = 1;
            } else if (vec == 1) {
                vec = 0;
            } else if (vec == 2) {
                vec = 3;
            } else if (vec == 3) {
                vec = 2;
            }
        }

        if (vec == 0) {
            int temp = dice[1][2];
            int temp2 = dice[3][1];
            for (int i = 1; i >= 0; i--) {
                dice[1][i+1] = dice[1][i];
            }
            dice[1][0] = temp2;
            dice[3][1] = temp;
        } else if (vec == 1) {
            int temp = dice[1][0];
            int temp2 = dice[3][1];
            for (int i = 0; i < 2; i++) {
                dice[1][i] = dice[1][i+1];
            }
            dice[1][2] = temp2;
            dice[3][1] = temp;
        } else if (vec == 2) {
            int temp = dice[3][1];
            for (int i = 3; i > 0; i--) {
                dice[i][1] = dice[i-1][1];
            }
            dice[0][1] = temp;
        } else if (vec == 3) {
            int temp = dice[0][1];
            for (int i = 0; i < 3; i++) {
                dice[i][1] = dice[i + 1][1];
            }
            dice[3][1] = temp;
        }

        int sx = x + idx[vec];
        int sy = y + idy[vec];

        if (dice[3][1] > map[sx][sy]) {
            if (vec == 0) {
                vec = 2;
            } else if (vec == 1) {
                vec = 3;
            } else if (vec == 2) {
                vec = 1;
            } else {
                vec = 0;
            }
        } else if (dice[3][1] < map[sx][sy]) {
            if (vec == 0) {
                vec = 3;
            } else if (vec == 1) {
                vec = 2;
            } else if (vec == 2) {
                vec = 0;
            } else {
                vec = 1;
            }
        }

        return new Info(sx, sy, vec);

    }

    public static void bfs(int x, int y, int[] idx, int[] idy, boolean[][] visits, int N, int M, int[][] map) {


        Deque<Info> dq = new ArrayDeque<>();
        dq.add(new Info(x, y, 0));

        while (!dq.isEmpty()) {
            cnt++;
            Info info = dq.poll();
            x = info.x;
            y = info.y;

            for (int i = 0; i < 4; i++) {
                int sx = x + idx[i];
                int sy = y + idy[i];

                if (sx < 0 || sy < 0 || sx >= N || sy >= M || visits[sx][sy] || map[sx][sy] != map[x][y]) {
                    continue;
                }

                visits[sx][sy] = true;
                dq.add(new Info(sx, sy, 0));
            }

        }
    }
}
