package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B9663 {

    public static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] arr = new int[N][N];

        fn(0, 0, N, arr);

        System.out.println(answer);

    }

    public static void fn(int position, int cnt, int N, int[][] arr) {
        if (cnt == N) {
            answer++;
            return;
        }

        for (int i = position; i < N * N; i++) {
            int x = i / N;
            int y = i % N;

            if (isQueenPositionAble(x, y, N, arr)) {
                arr[x][y] = 1;
                fn(i + 1, cnt + 1, N, arr);
                arr[x][y] = 0;
            }
        }
    }

    public static boolean isQueenPositionAble(int x, int y, int N, int[][] arr) {
        int[] idx = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] idy = {1, -1, 0, 1, -1, -1, 1, 0};

        for (int i = 0; i < 8; i++) {
            int sx = x + idx[i];
            int sy = y + idy[i];

            while (isRangeCheck(sx, sy, N)) {
                if (arr[sx][sy] == 1) {
                    return false;
                }
                sx += idx[i];
                sy += idy[i];
            }
        }

        return true;
    }

    public static boolean isRangeCheck(int x, int y, int N) {
        return !(x < 0 || y < 0 || x >= N || y >= N);
    }

}
