package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B16137 {
    public static class Info {
        int x;
        int y;
        int length;

        public Info(int x, int y, int length) {
            this.x = x;
            this.y = y;
            this.length = length;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(bfs(N, M, arr));
    }


    public static int bfs(int N, int M, int[][] arr) {
        int[] idx = {0, 0, 1, -1};
        int[] idy = {1, -1, 0, 0};
        Queue<Info> q = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        q.add(new Info(0, 0, 0));
        visit[0][0] = true;

        while (!q.isEmpty()) {
            Info info = q.poll();

            if (info.x == N - 1 && info.y == N - 1) {
                return info.length;
            }

            for (int i = 0; i < 4; i++) {
                int sx = info.x + idx[i];
                int sy = info.y + idy[i];

                if (sx < 0 || sy < 0 || sx >= N || sy >= N || visit[sx][sy]) {
                    continue;
                }

                if (arr[sx][sy] == 1 || (arr[sx][sy] > 1 && info.length + 1 % arr[sx][sy] == 0) || (arr[sx][sy] == 0 && info.length + 1 % M == 0)) {
                    visit[sx][sy]= true;
                    q.add(new Info(sx, sy, info.length + 1));
                }
            }
            q.add(new Info(info.x, info.y, info.length+1));
        }

        return -1;
    }


}
