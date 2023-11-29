package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B16137 {
    public static class Info implements Comparable<Info> {
        int x;
        int y;
        int time;
        int cnt;


        public Info(int x, int y, int time, int cnt) {
            this.x = x;
            this.y = y;
            this.time = time;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Info o) {
            return this.time - o.time;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][N];


        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = bfs(N, M, map);

        System.out.println(answer);

    }

    public static int bfs(int N, int M, int[][] map) {
        PriorityQueue<Info> pq = new PriorityQueue<>();
        int[][][] mtime = new int[2][N][N];
        int[] idx = {0, 0, -1, 1};
        int[] idy = {-1, 1, 0, 0};
        int res = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(mtime[j][i], 5000);
            }
        }

        pq.add(new Info(0, 0, 0, 0));
        mtime[0][0][0] = 0;

        while (!pq.isEmpty()) {
            Info info = pq.poll();

            if (info.x == N - 1 && info.y == N - 1) {
                res = info.time;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + info.x;
                int sy = idy[i] + info.y;

                if (sx < 0 || sy < 0 || sx >= N || sy >= N) {
                    continue;
                }

                if (info.cnt == 1) {
                    if (map[sx][sy] == 0) {
                        continue;
                    }

                    if ((info.time + 1) % map[sx][sy] == 0 && mtime[1][sx][sy] > info.time + 1) {
                        mtime[1][sx][sy] = info.time + 1;
                        pq.add(new Info(sx, sy, info.time + 1, 1));
                    }
                } else {
                    if (map[sx][sy] == 0) {
                        if ((info.time + 1) % M == 0 && mtime[1][sx][sy] > info.time + 1) {
                            mtime[1][sx][sy] = info.time + 1;
                            pq.add(new Info(sx, sy, info.time + 1, 1));
                        }
                    }else if ((info.time + 1) % map[sx][sy] == 0 && mtime[0][sx][sy] > info.time + 1) {
                        mtime[0][sx][sy] = info.time + 1;
                        pq.add(new Info(sx, sy, info.time + 1, 0));
                    }
                }
            }
        }

        return res;
    }
}
