package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14712_3 {
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][M];
        int product = N * M;

        dfs(N, M, arr, product, 0);

        System.out.println(answer);

    }

    public static void dfs(int N, int M, int[][] arr, int product, int index) {
        if (product == index) {
            answer++;
            return;
        }

        int x = index / M;
        int y = index % M;

        dfs(N, M, arr, product, index + 1);
        if (x - 1 < 0 || y - 1 < 0 || arr[x][y - 1] != 1 || arr[x - 1][y - 1] != 1 || arr[x - 1][y] != 1) {
            arr[x][y] = 1;
            dfs(N, M, arr, product, index + 1);
            arr[x][y] = 0;
        }

    }

}
