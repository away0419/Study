package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B9084_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            int[] coins = new int[N];
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(br.readLine());
            int[] moneys = new int[M + 1];

            for (int i = 0; i < N; i++) {
                coins[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < N; i++) {
                for (int j = coins[i]; j < M+1; j++) {
                    int diff = j - coins[i];
                    moneys[j] += moneys[diff];
                    if (diff == 0) {
                        moneys[j]++;
                    }
                }
            }
            sb.append(moneys[M]).append("\n");
        }

        System.out.println(sb);
    }
}
