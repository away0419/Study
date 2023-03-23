package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
연속 합
N : 주어진 수
arr[] : 주어진 배열
 */
public class B1912 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int dp[] = new int[N];
        dp[0] = Integer.parseInt(st.nextToken());
        int max = dp[0];
        for (int i = 1; i < N; i++) {
            int k = Integer.parseInt(st.nextToken());
            dp[i] = Math.max(dp[i - 1] + k, k);
            max = Math.max(max, dp[i]);
        }
        System.out.println(max);
    }

}
