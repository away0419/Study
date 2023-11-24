package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
점프 점프
N : 주어지는 수
dp[] : 점프 최소 횟수
arr[] : 주어지는 배열
 */
public class B11060 {
    static int N;
    static int[] dp, arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];
        dp = new int[N];
        Arrays.fill(dp, 2000);
        dp[0] = 0;

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        fn(0);
        int res = dp[N - 1] == 2000 ? -1 : dp[N - 1];
        System.out.println(res);

    }

    public static void fn(int idx) {
        for (int i = 1; i <= arr[idx]; i++) {
            if (idx + i >= N) {
                break;
            }
            if (dp[idx + i] > dp[idx] + 1) {
                dp[idx + i] = dp[idx] + 1;
                fn(idx + i);
            }
        }
    }

}
