package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 폴리오미노
 * S : 주어진 문자열
 * 
 */
public class B1343 {
	static String S;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = br.readLine();
		StringBuilder sb = new StringBuilder();
		int cnt = 0;
		for (int i = 0; i < S.length(); i++) {
			if (S.charAt(i) == 'X') {
				cnt++;
			} else {
				if (cnt % 2 != 0) {
					System.out.println(-1);
					return;
				} else {
					int k = cnt / 4;

					for (int j = 0; j < k; j++) {
						sb.append("AAAA");
					}
					if (cnt % 4 != 0) {
						sb.append("BB");
					}

					sb.append(".");
					cnt = 0;
				}
			}

		}

		if (cnt % 2 != 0) {
			System.out.println(-1);
			return;
		} else {
			int k = cnt / 4;

			for (int j = 0; j < k; j++) {
				sb.append("AAAA");
			}
			if (cnt % 4 != 0) {
				sb.append("BB");
			}

			cnt = 0;
		}

		System.out.println(sb);

	}

}
