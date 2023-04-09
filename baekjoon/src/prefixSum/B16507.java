package prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
어두운 건 무서워
R, C : 행 렬
TC : 테스트 케이스
dp[][] : 누적 합

 */
public class B16507 {
    static int R, C, TC;
    static long dp[][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        TC = Integer.parseInt(st.nextToken());
        dp = new long[R + 1][C + 1];
        for (int i = 1; i <= R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= C; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + Integer.parseInt(st.nextToken());
            }
        }

        StringBuilder sb = new StringBuilder();
        while (TC-- > 0) {
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken()) - 1;
            int c1 = Integer.parseInt(st.nextToken()) - 1;
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            int cnt = (r2 - r1) * (c2 - c1);

            long sum = dp[r2][c2] - dp[r1][c2] - dp[r2][c1] + dp[r1][c1];
            long answer = sum / cnt;
            sb.append(answer).append("\n");
        }

        System.out.println(sb);

    }

}
