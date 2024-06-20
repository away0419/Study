package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B17244 {

    public static class Info {
        int x;
        int y;
        int leng;
        int bit;

        public Info(int x, int y, int leng, int bit) {
            this.x = x;
            this.y = y;
            this.leng = leng;
            this.bit = bit;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][M];
        int targetCnt = 0;
        Info start = null;
        Info end = null;
        int allVisitBit = 1;

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = str.charAt(j);

                if (arr[i][j] == '#') {
                    arr[i][j] = -1;
                } else if (arr[i][j] == 'X') {
                    targetCnt++;
                    arr[i][j] = targetCnt;
                    allVisitBit |= 1 << targetCnt;
                } else if (arr[i][j] == 'S') {
                    start = new Info(i, j, 0, 1);
                    arr[i][j] = 0;
                } else if (arr[i][j] == 'E') {
                    end = new Info(i, j, 0, 0);
                    arr[i][j] = 0;
                } else {
                    arr[i][j] = 0;
                }
            }
        }

        System.out.println(bfs(N, M, arr, targetCnt, allVisitBit, start, end));


    }

    public static int bfs(int N, int M, int[][] arr, int targetCnt, int allVisitBit, Info start, Info end) {
        int[] idx = {0, 0, 1, -1};
        int[] idy = {1, -1, 0, 0};
        boolean[][][] visits = new boolean[N][M][1 << targetCnt + 1];
        Queue<Info> q = new ArrayDeque<>();

        q.add(start);
        visits[start.x][start.y][1] = true;

        while (!q.isEmpty()) {
            Info cur = q.poll();

            if (cur.bit == allVisitBit && cur.x == end.x && cur.y == end.y) {
                return cur.leng;
            }

            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + cur.x;
                int sy = idy[i] + cur.y;

                if (sx < 0 || sy < 0 || sx >= N || sy >= M || arr[sx][sy] == -1 || visits[sx][sy][cur.bit]) {
                    continue;
                }

                if (arr[sx][sy] > 0) {
                    int bit = cur.bit | 1 << arr[sx][sy];
                    q.add(new Info(sx, sy, cur.leng + 1, bit));
                    visits[sx][sy][bit] = true;
                } else {
                    q.add(new Info(sx, sy, cur.leng + 1, cur.bit));
                    visits[sx][sy][cur.bit] = true;
                }

            }

        }
        return -1;

    }


}
