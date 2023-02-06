package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * UCPC는 무엇의 약자일까
 * S : 문자열
 */
public class B15904 {
	static String S;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = br.readLine();
		String pattern = "UCPC";

		int j = 0;
		for (int i = 0; i < S.length(); i++) {
			if (S.charAt(i) == pattern.charAt(j)) {
				j++;
				if (j == 4) {
					break;
				}
			}
		}

		if (j == 4) {
			System.out.println("I love UCPC");
		} else {
			System.out.println("I hate UCPC");
		}

	}

}
