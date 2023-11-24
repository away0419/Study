package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
그림
N, M : 행렬
map[][] : 주어진 배열
 */
public class B1926 {
    static int N, M, CNT, MAX, SUM;
    static int[][] map;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 1) {
                    CNT++;
                    map[i][j] = 0;
                    dfs(i, j);
                    MAX = Math.max(MAX, SUM);
                    SUM = 0;
                }
            }
        }

        System.out.println(CNT);
        System.out.println(MAX);

    }

    public static void dfs(int x, int y) {
        SUM++;
        int[] idx = {0, 0, -1, 1};
        int[] idy = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx < 0 || sy < 0 || sx >= N || sy >= M || map[sx][sy] == 0) {
                continue;
            }
            map[sx][sy] = 0;
            dfs(sx, sy);
        }

    }

}
