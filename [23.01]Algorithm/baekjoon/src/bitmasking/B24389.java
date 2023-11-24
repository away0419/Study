package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 2의 보수
 * N: 주어진수
 */
public class B24389 {
	static int N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		int k = (~N) + 1;
		int s = N ^ k;

		System.out.println(Integer.bitCount(s));

	}

}
