package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 수열
 * N : 수열 길이
 * arr[] : 수열
 * 
 * 
 */
public class B2491 {
	static int N, arr[], inMax, deMax, inCnt, deCnt;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int in = Integer.parseInt(st.nextToken());
		int de = in;
		inCnt = deCnt = 1;

		for (int i = 1; i < N; i++) {
			int k = Integer.parseInt(st.nextToken());
			if (in <= k) {
				inCnt++;
			} else {
				inMax = Math.max(inCnt, inMax);
				inCnt = 1;
			}

			if (de >= k) {
				deCnt++;
			} else {
				deMax = Math.max(deCnt, deMax);
				deCnt = 1;
			}
			deMax = Math.max(deCnt, deMax);
			inMax = Math.max(inCnt, inMax);

			in = k;
			de = k;
		}
		inMax = Math.max(inCnt, inMax);
		System.out.println(Math.max(inMax, deMax));
	}

}
