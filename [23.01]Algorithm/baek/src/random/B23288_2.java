package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B23288_2 {
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
        int[][] maps = new int[N][M];
        int[][] dice = new int[4][3];

        dice[0][1] = 2;
        dice[1][0] = 4;
        dice[1][1] = 1;
        dice[1][2] = 3;
        dice[2][1] = 5;
        dice[3][1] = 6;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

    }

    public static void diceRotation(int vec, int[][] dice) {
        if (vec == 0) {
            int temp = dice[1][2];
            dice[1][2] = dice[1][1];
            dice[1][1] = dice[1][0];
            dice[1][0] = dice[3][1];
            dice[3][1] = temp;
        } else if (vec == 1) {
            int temp = dice[1][0];
            dice[1][0] = dice[1][1];
            dice[1][1] = dice[1][2];
            dice[1][2] = dice[3][1];
            dice[3][1] = temp;
        } else if (vec == 2) {
            int temp = dice[3][1];
            dice[3][1] = dice[2][1];
            dice[2][1] = dice[1][1];
            dice[1][1] = dice[0][1];
            dice[0][1] = temp;
        } else if (vec == 3) {
            int temp = dice[0][1];
            dice[0][1] = dice[1][1];
            dice[1][1] = dice[2][1];
            dice[2][1] = dice[3][1];
            dice[3][1] = temp;
        }
    }

    public static void bfs(int x, int y, int[][] maps, int N, int M) {
        Queue<Info> q = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][M];
        int[] idx = {0, 0, -1, 1};
        int[] idy = {1, -1, 0, 0};

        q.add(new Info(x, y, 0));
        visit[x][y] = true;

        while (!q.isEmpty()) {
            Info info = q.poll();


            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + info.x;
                int sy = idy[i] + info.y;

                if(sx<0 || sy<0 ||sx>=N || sy>=M || visit[sx][sy] || maps[sx][sy] != maps[info.x][info.y]){
                    continue;
                }

                visit[sx][sy] = true;




            }

        }

    }
}
