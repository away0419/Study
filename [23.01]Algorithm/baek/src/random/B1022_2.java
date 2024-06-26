package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1022_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        int r1 = Integer.parseInt(st.nextToken());
        int c1 = Integer.parseInt(st.nextToken());
        int r2 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());
        int n = r2 - r1;
        int m = c2 - c1;
        int[][] arr = new int[n + 1][m + 1];

        int[] idx = {0, -1, 0, 1};
        int[] idy = {1, 0, -1, 0};
        int x = 0;
        int y = 0;
        int value = 1;
        int vec = 0;
        int stack = 0;
        int turnCnt = 1;

        while (isAllValueInput(r1, c1, r2, c2, arr)) {
            if (x <= r2 && x >= r1 && y >= c1 && y <= c2) {
                arr[x - r1][y - c1] = value;
            }

            x += idx[vec];
            y += idy[vec];
            value++;
            stack++;

            if (stack == turnCnt) {
                stack = 0;
                vec = (vec + 1) % 4;
                if (vec == 0 || vec == 2) {
                    turnCnt++;
                }
            }
        }

        int maxLetterLeng = (int) Math.log10(value - 1);

        for (int i = 0; i <= r2 - r1; i++) {
            for (int j = 0; j <= c2 - c1; j++) {
                int blankCnt = maxLetterLeng - (int) Math.log10(arr[i][j]);
                for (int k = 0; k < blankCnt; k++) {
                    sb.append(" ");
                }
                sb.append(arr[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);

    }

    public static boolean isAllValueInput(int r1, int c1, int r2, int c2, int[][] arr) {
        return arr[0][0] == 0 || arr[r2 - r1][0] == 0 || arr[0][c2 - c1] == 0 || arr[r2 - r1][c2 - c1] == 0;
    }
}
