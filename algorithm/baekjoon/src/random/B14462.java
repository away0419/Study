package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14462 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] rs = new int[N+1];
        int[] ls = new int[N+1];
        long[][] dp = new long[N+1][N+1];

        for (int i =1; i <= N; i++) {
            ls[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 1; i <= N; i++) {
            rs[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if(Math.abs(ls[i] -rs[j])<=4 ){
                    long max = 0L;
                    for (int k = 1; k < j; k++) {
                        max = Math.max(max, dp[i-1][k]);
                    }
                    dp[i][j] = max+1;
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        long answer = 0;
        for (int i = 0; i <= N; i++) {
            answer = Math.max(answer, dp[N][i]);
        }

        System.out.println(answer);

    }

}
