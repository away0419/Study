package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
상자 넣기
N : 주어진 갯수
 */
public class B1965 {
    static int N;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        dp = new int[N];
        dp[0] = Integer.parseInt(st.nextToken());
        int cnt = 0;

        for (int i = 1; i < N; i++) {
            int k = Integer.parseInt(st.nextToken());
            if (dp[cnt] < k) {
                dp[++cnt] = k;
            } else {
                bs(k, cnt);
            }
        }
        System.out.println(cnt + 1);
    }

    public static void bs(int value, int cnt) {
        int start = 0;
        int end = cnt;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (dp[mid] < value) {
                start = mid + 1;
            } else {
                if (dp[mid] == value) {
                    return;
                }
                end = mid - 1;
            }
        }
        dp[start] = value;
    }
}
