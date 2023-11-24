package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B2140 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        int[] idx = {0, 0, 1, 1, -1, -1, 1, -1};
        int[] idy = {1, -1, 1, -1, 1, -1, 0, 0};
        int answer = 0;

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                char ch = str.charAt(j);
                map[i][j] = ch - '0';

            }
        }

        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < n - 1; j++) {

                boolean check = true;
                for (int k = 0; k < 8; k++) {
                    int sx = idx[k] + i;
                    int sy = idy[k] + j;

                    if (map[sx][sy] == 0) {
                        check = false;
                        break;
                    }
                }

                if (!check) {
                    continue;
                }

                for (int k = 0; k < 8; k++) {
                    int sx = idx[k] + i;
                    int sy = idy[k] + j;
                    if (map[sx][sy] > 0) {
                        map[sx][sy]--;
                    }

                }
                answer++;

            }
        }
        System.out.println(answer);
    }

}
