package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B17244 {
    public static class Info {
        int x;
        int y;
        int length;
        int bit;

        public Info(int x, int y, int length, int bit) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.bit = bit;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int[][] arr1 = new int[N][M];
        int[][] arr2 = new int[N][M];
        Info start = null, end = null;
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                arr1[i][j] = str.charAt(j);
                arr2[i][j] = -1;
                if (arr1[i][j] == 'S') {
                    start = new Info(i, j, 0, 0);
                } else if (arr1[i][j] == 'E') {
                    end = new Info(i, j, 0, 0);
                } else if (arr1[i][j] == 'X') {
                    arr2[i][j] = cnt++;
                }
            }
        }

        int answer = bfs(N, M, arr1, arr2, start, end, cnt);

        System.out.println(answer);

    }


    public static int bfs(int N, int M, int[][] arr1, int[][] arr2, Info start, Info end, int cnt) {
        int result = 0;
        int allVisit = 0;
        int[] idx = {0, 0, 1, -1};
        int[] idy = {1, -1, 0, 0};
        Queue<Info> q = new ArrayDeque<>();
        boolean[][][] visit = new boolean[N][M][1 << cnt];

        for (int i = 0; i < cnt; i++) {
            allVisit += (1 << i);
        }
        q.add(start);
        visit[start.x][start.y][0] = true;

        while (!q.isEmpty()) {
            Info info = q.poll();
            int bit = info.bit;
            int length = info.length;

            if (bit == allVisit && info.x == end.x && end.y == info.y) {
                return length;
            }

            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + info.x;
                int sy = idy[i] + info.y;

                if (sx < 0 || sy < 0 || sx >= N || sy >= M || arr1[sx][sy] == '#' || visit[sx][sy][info.bit]) {
                    continue;
                }
                if (arr2[sx][sy] != -1) {
                    q.add(new Info(sx, sy, length + 1, bit | (1 << arr2[sx][sy])));
                    visit[sx][sy][bit | (1 << arr2[sx][sy])] = true;
                } else {
                    q.add(new Info(sx, sy, length + 1, bit));
                    visit[sx][sy][bit] = true;
                }
            }

        }

        return result;
    }
}
