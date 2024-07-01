package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1107_2 {
    public static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        boolean[] number = new boolean[10];

        if (M != 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                int index = Integer.parseInt(st.nextToken());
                number[index] = true;
            }
        }

        if (N == 100) {
            System.out.println(0);
            return;
        }

        answer = Math.abs(N - 100);

        fn(0, 0, N, number);

        System.out.println(answer);

    }

    public static void fn(int cnt, int cur, int N, boolean[] number) {

        if (cnt !=0 && Math.abs(cur - N) + cnt < answer) {
            answer = Math.abs(cur - N) + cnt;
        }


        if (cnt == 6) {
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (number[i]) {
                continue;
            }
            fn(cnt + 1, cur * 10 + i, N, number);
        }
    }

}
