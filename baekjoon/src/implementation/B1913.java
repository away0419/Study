package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
N : 주어지는 홀수
K : 주어지는 자연수
map[][] : 저장 공간
 */
public class B1913 {
    static int N, K, map[][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
        map = new int[N][N];

        int a = N / 2;
        int b = N / 2;
        int vec = 0;
        int cnt = 0;

        StringBuilder sb = new StringBuilder();
        if (K == 1) {
            sb.append(a+1).append(" ").append(b+1);
        }
        map[a][b] = 1;

        loop:
        while (true) {
            vec %= 4;
            if (vec == 0) {
                cnt++;
                for (int i = 0; i < cnt; i++) {
                    if(a-1 == -1){
                        break loop;
                    }
                    map[a - 1][b] = map[a--][b] + 1;
                    if (map[a][b] == K) {
                        sb.append(a+1).append(" ").append(b+1);
                    }
                }
            } else if (vec == 1) {
                for (int i = 0; i < cnt; i++) {
                    map[a][b + 1] = map[a][b++] + 1;
                    if (map[a][b] == K) {
                        sb.append(a+1).append(" ").append(b+1);
                    }
                }
            } else if (vec == 2) {
                cnt++;
                for (int i = 0; i < cnt; i++) {
                    map[a + 1][b] = map[a++][b] + 1;
                    if (map[a][b] == K) {
                        sb.append(a+1).append(" ").append(b+1);
                    }
                }
            } else if (vec == 3) {
                for (int i = 0; i < cnt; i++) {
                    map[a][b - 1] = map[a][b--] + 1;
                    if (map[a][b] == K) {
                        sb.append(a+1).append(" ").append(b+1);
                    }
                }
            }

            vec++;

        }
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb2.append(map[i][j]).append(" ");
            }
            sb2.append("\n");
        }
        sb2.append(sb);

        System.out.println(sb2);
    }

}
