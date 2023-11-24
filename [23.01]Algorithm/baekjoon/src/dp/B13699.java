package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 점화식
 * N :  
 */
public class B13699 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		long dp[] = new long[N+1];
		dp[0] =1;
		
		for (int i = 1; i <= N; i++) {
			int j=0;
			while(j<i/2) {
				dp[i] += 2*dp[j]*dp[i-j-1]; 
				j++;
			}
			if(i%2!=0) {
				dp[i] += dp[i/2]*dp[i/2];
			}
			
		}
		System.out.println(dp[N]);
	}

}
