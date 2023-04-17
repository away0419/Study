package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
병든 나이트

 */
public class B1783 {
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int res = 1;
        if (N == 2) {
            res = Math.min(4, (M + 1) / 2);
        } else if (N >= 3) {
            if (M < 7) {
                res = Math.min(4, M);
            } else {
                res = M - 2;
            }
        }
        System.out.println(res);

    }

}
