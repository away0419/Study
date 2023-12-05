package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B10978 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        long[] answer = new long[21];
        StringBuilder sb = new StringBuilder();

        answer[2] = 1;

        for (int i = 3; i < 21; i++) {
            answer[i] = (answer[i - 2] + answer[i - 1]) * (i - 1);
        }


        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            sb.append(answer[N]).append("\n");
        }

        System.out.println(sb);
    }


}
