package greedy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 뒤집기
 * S : 문자열
 * answer : 최소 뒤집는 수
 * s0 : 0 뒤집을 경우 수
 * s1 : 1 뒤집을 경우 수
 */
public class B1439 {
	static String S;
	static int answer, s0, s1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = br.readLine();

		int s = S.charAt(0);
		for (int i = 1; i < S.length(); i++) {
			if (s != S.charAt(i)) {
				if (s == '0') {
					s0++;
					s = S.charAt(i);
				} else {
					s1++;
					s = S.charAt(i);
				}
			}
		}

		if (S.charAt(S.length() - 1) == '0') {
			s0++;
		} else {
			s1++;
		}

		answer = Math.min(s1, s0);
		System.out.println(answer);
	}

}
