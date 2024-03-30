package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B2617 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][N];
        int INF = 101;
        int[] cols = new int[N];
        int[] rows = new int[N];
        int mid = (N + 1) / 2;
        int answer = 0;

        for (int i = 0; i < N; i++) {
            Arrays.fill(arr[i], INF);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            arr[a][b] = 1;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    if(j == k) continue;
                    arr[j][k] = Math.min(arr[j][k], arr[j][i] + arr[i][k]);
                }
            }
        }


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j || arr[i][j] == INF) {
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
