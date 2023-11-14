package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
단지 번호 붙이기
N : 행, 렬
map[][] : 주어지는 2차원 배열

 */
public class B2667 {
    static int N, cnt;
    static char map[][];
    static List<Integer> list;
    static int[] idx = {0, 0, -1, 1};
    static int[] idy = {-1, 1, 0, 0};
    static boolean[][] visit;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new char[N][];
        list = new ArrayList<>();
        visit = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visit[i][j] && map[i][j] == '1') {
                    cnt = 1;
                    visit[i][j] = true;
                    dfs(i, j);
                    list.add(cnt);
                }
            }
        }
        System.out.println(list.size());
        list.stream().sorted().forEach(System.out::println);

    }

    public static void dfs(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int sx = x + idx[i];
            int sy = y + idy[i];

            if (sx < 0 || sy < 0 || sx >= N || sy >= N || map[sx][sy] == '0' || visit[sx][sy]) {
                continue;
            }

            visit[sx][sy] = true;
            cnt++;
            dfs(sx, sy);
        }
    }
}
