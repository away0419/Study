package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14712_2 {
    public static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][M];

        combination(N, M, 0, arr);

        System.out.println(answer);
    }

    public static void combination(int N, int M, int index, int[][] arr) {

        if (N * M == index) {
            answer++;
            return;
        }

        int x = index / M;
        int y = index % M;

        combination(N, M, index + 1, arr);
        if (x - 1 < 0 || y - 1 < 0 || arr[x][y - 1] != 1 || arr[x - 1][y - 1] != 1 || arr[x - 1][y] != 1) {
            arr[x][y] = 1;
            combination(N, M, index + 1, arr);
            arr[x][y] = 0;
        }
    }
}
