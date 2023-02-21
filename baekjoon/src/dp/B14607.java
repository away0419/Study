package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 피자(Large)
 * N : 피자
 */
public class B14607 {
	static long N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Long.parseLong(br.readLine());
		System.out.print((N * N - N) / 2);

	}

}
