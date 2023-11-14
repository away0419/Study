package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
T : 테스트케이스
N, M : 세로 가로
map[][] : 주어진 배열
 */
public class B1012 {
    static int T, N, M, K;
    static boolean visit[][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            int answer = 0;
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            visit = new boolean[N][M];

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                visit[y][x] = true;
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (visit[i][j]) {
                        answer++;
                        dfs(i, j);
                    }
                }
            }
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }


    public static void dfs(int x, int y) {
        int idx[] = {0, 0, -1, 1};
        int idy[] = {1, -1, 0, 0};
        visit[x][y] = false;
        for (int i = 0; i < 4; i++) {
            int sx = idx[i] + x;
            int sy = idy[i] + y;
            if (sx < 0 || sy < 0 || sx >= N || sy >= M || !visit[sx][sy]) {
                continue;
            } else {
                dfs(sx, sy);
            }
        }
    }
}
