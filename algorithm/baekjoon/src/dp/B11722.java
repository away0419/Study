package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
가장 긴 감소하는 부분 수열
N : 주어지는 숫자 크기
dp : 가장 긴 부분 수열을 채우는 용도의 배열
 */
public class B11722 {
    static int N, CNT;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        dp[0] = Integer.parseInt(st.nextToken());

        for (int i = 1; i < N; i++) {
            int k = Integer.parseInt(st.nextToken());
            if (k < dp[CNT]) {
                dp[++CNT] = k;
            } else {
                bs(k);
            }
        }
        System.out.println(CNT+1);
    }

    public static void bs(int value) {
        int start = 0;
        int end = CNT;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (dp[mid] < value) {
                end = mid - 1;
            } else {
                if (dp[mid] == value) {
                    return;
                }
                start = mid + 1;
            }
        }
        dp[start] = value;
    }
}
