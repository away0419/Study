package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1497 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        long[] guitars = new long[n];
        int[] sings = new int[m+1];

        for (int i = 0; i <= m; i++) {
            sings[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String guitar = st.nextToken();
            String singAble = st.nextToken().toUpperCase();
            for (int j = 0; j < m; j++) {
                if (singAble.charAt(j) == 'Y') {
                    guitars[i] |= 1L << j;
                }
            }
        }

        com(0, 0, 0, n, m, guitars, sings);

        for (int i = m; i >= 0; i--) {
            if (sings[i] != Integer.MAX_VALUE) {
                System.out.println(sings[i]);
                return;
            }
        }

        System.out.println(-1);
    }

    public static void com(int cur, long bit, int cnt, int n, int m, long[] guitars, int[] sings) {
        int bitCnt = Long.bitCount(bit);
        if (bitCnt != 0) {
            sings[bitCnt] = Math.min(sings[bitCnt], cnt);
//            System.out.println(Integer.toBinaryString(bit) + " : " + cnt);

            if (bit == (1L << m) - 1) {
                return;
            }
        }

        for (int i = cur; i < n; i++) {
            if(guitars[i] == 0 || (bit & guitars[i]) == guitars[i]){
                continue;
            }
            com(i + 1, bit | guitars[i], cnt + 1, n, m, guitars, sings);
        }
    }
}
