package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 정수 제곱근
 */
public class B2471 {
	static long N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int leng = (str.length()-1) / 2;
		N = Long.parseLong(str);
		System.out.printf("%.0f",bs(leng));
	}

	public static double bs(int leng) {
		long start = (long)Math.pow(10, leng)-1;
		long end = (long)Math.pow(10, leng+1);
		while (start <= end) {
			long mid = (start + end) / 2;
			System.out.printf("%d %d %d %.0f \n",start, end, mid, ((double)mid*mid));
			if ((long)((double)mid*mid) < N) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}

		return start;
	}

}
