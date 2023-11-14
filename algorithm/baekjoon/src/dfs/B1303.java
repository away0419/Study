package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
전쟁 - 전투
N, M : 행렬
map[][] : 주어진 배열
 */
public class B1303 {
    static int N, M, SUM;
    static int[][] map;
    static boolean[][] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visit = new boolean[N][M];
        int[] answer = new int[2];

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visit[i][j]) {
                    visit[i][j] = true;
                    SUM = 0;
                    dfs(i, j);
                    if (map[i][j] == 'W') {
                        answer[0] += SUM * SUM;
                    } else {
                        answer[1] += SUM * SUM;
                    }
                }
            }
        }

        System.out.println(answer[0] + " " + answer[1]);

    }

    public static void dfs(int x, int y) {
        int[] idx = {-1, 1, 0, 0};
        int[] idy = {0, 0, -1, 1};
        SUM++;

        for (int i = 0; i < 4; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;

            if (sx < 0 || sy < 0 || sx >= N || sy >= M || visit[sx][sy] || map[x][y] != map[sx][sy]) {
                continue;
            }
            visit[sx][sy] = true;
            dfs(sx, sy);

        }
    }
}
