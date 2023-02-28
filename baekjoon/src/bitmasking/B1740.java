package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B1740 {

	static long N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Long.parseLong(br.readLine());

		String str = Long.toBinaryString(N);
		long answer = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(str.length() - 1 - i) == '1') {
				
				long c =1;
				for (int j = 0; j < i; j++) {
					c*=3;
				}
				answer+=c;
			}
		}
		System.out.println(answer);
	}

}
