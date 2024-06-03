package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B2617_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][N];
        int answer = 0;
        int INF = 101;
        int mid = (N + 1) / 2;

        for (int i = 0; i < N; i++) {
            Arrays.fill(arr[i], INF);
        }


        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            arr[a][b] = 1;
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                if (arr[i][k] == 0) {
                    continue;
                }
                for (int j = 0; j < N; j++) {
                    if (arr[j][k] == 0) {
                        continue;
                    }

                    if (arr[i][k] == 0) {
                        continue;
                    }

                    arr[i][j] = Math.min(arr[i][j], arr[i][k] + arr[k][j]);
                }
            }
        }


        int[] rows = new int[N];
        int[] cols = new int[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == 101) {
                    continue;
                }
                rows[i]++;
                cols[j]++;
            }
        }

        for (int i = 0; i < N; i++) {
            if (cols[i] >= mid) answer++;
            if (rows[i] >= mid) answer++;
        }

        System.out.println(answer);

    }
}
