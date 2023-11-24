package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B7579 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
        int[] cost = new int[N];
        int[] dp = new int[10001];

        st = new StringTokenizer(br.readLine());
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            cost[i] = Integer.parseInt(st2.nextToken());
        }

        for (int i = 0; i < N; i++) {
            for (int j = 10000; j >= cost[i]; j--) {
                dp[j] = Math.max(dp[j - cost[i]] + arr[i], dp[j]);
            }
        }
        int answer = 0;
        for (int i = 0; i <= 10000; i++) {
            if (dp[i] >= M) {
                answer = i;
                break;
            }
        }

        System.out.println(answer);

    }
}
