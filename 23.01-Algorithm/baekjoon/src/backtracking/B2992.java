package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 크면서 작은수
 * N : 수
 * answer : 답
 */
public class B2992 {
	static int N, leng, answer, arr[];
	static boolean visit[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		answer = Integer.MAX_VALUE;
		leng = str.length();
		N = Integer.parseInt(str);
		arr = new int[leng];
		visit = new boolean[leng];
		for (int i = 0; i < leng; i++) {
			arr[i] = str.charAt(i);
		}

		fn(0, "");
		if (answer == Integer.MAX_VALUE)
			System.out.println(0);
		else
			System.out.println(answer);

	}

	public static void fn(int cnt, String str) {
		if (cnt == leng) {
			int s = Integer.parseInt(str);
			if (s > N) {
				answer = Math.min(answer, s);
			}
			return;
		}

		for (int i = 0; i < leng; i++) {
			if (visit[i]) {
				continue;
			}
			visit[i] = true;
			fn(cnt + 1, str + (char) arr[i]);
			visit[i] = false;
		}
	}

}
