package dp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
가장 큰 증가하는 부분 수열

 */
public class B11055 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int dp[] = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        dp[0] = Integer.parseInt(st.nextToken());
        int start = dp[0];
        int max = dp[0];
        for (int i = 1; i < N; i++) {
            int k = Integer.parseInt(st.nextToken());
        }

    }
}
