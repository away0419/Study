package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
케빈 베이컨의 6단계
 */
public class B1389 {
    static int N, M;
    static int[][] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        dist = new int[N][N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            dist[a][b] = 1;
            dist[b][a] = 1;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (dist[j][i] == Integer.MAX_VALUE) {
                    continue;
                }
                for (int k = 0; k < N; k++) {
                    if (dist[i][k] == Integer.MAX_VALUE) {
                        continue;
                    }
                    if (dist[j][k] > dist[j][i] + dist[i][k]) {
                        dist[j][k] = dist[j][i] + dist[i][k];
                    }
                }
            }
        }

        int answer = 0;
        int min = Integer.MAX_VALUE;
        int[] sum = new int[N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum[i] += dist[i][j];
            }
        }

        for (int i = 0; i < N; i++) {
            if (min > sum[i]) {
                min = sum[i];
                answer = i;
            }
        }

        System.out.println(answer + 1);

    }
}
