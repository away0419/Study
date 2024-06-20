package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1022 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r1 = Integer.parseInt(st.nextToken());
        int c1 = Integer.parseInt(st.nextToken());
        int r2 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());
        int[] idx = {0, -1, 0, 1};
        int[] idy = {1, 0, -1, 0};
        int[][] arr = new int[r2 - r1 + 1][c2 - c1 + 1];
        int x = 0;
        int y = 0;
        int index = 0;
        int num = 1;
        int leng = 1;
        int cnt = 0;
        int max = 0;

        while (check(arr, r1, c1, r2, c2)) {
            if (x <= r2 && x >= r1 && y >= c1 && y <= c2) {
                arr[x - r1][y - c1] = num;
            }

            num++;
            cnt++;
            x += idx[index];
            y += idy[index];

            if (cnt == leng) {
                cnt = 0;
                if (index == 1 || index == 3) {
                    leng++;
                }
                index = (index + 1) % 4;
            }

        }
        max = num - 1;

        int maxLeng = (int) Math.log10(max);
        int len;

        for (int i = 0; i <= r2 - r1; i++) {
            for (int j = 0; j <= c2 - c1; j++) {
                len = maxLeng - (int) Math.log10(arr[i][j]);
                for (int k = 0; k < len; k++) {
                    System.out.print(" ");
                }
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }


    public static boolean check(int[][] arr, int r1, int c1, int r2, int c2) {
        return arr[0][0] == 0 || arr[r2 - r1][0] == 0 || arr[0][c2 - c1] == 0 || arr[r2 - r1][c2 - c1] == 0;
    }


}
