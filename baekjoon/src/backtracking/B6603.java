package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 로또
 * k : 주어진 배열 수
 * arr[] : 배열
 * answer : 정답
 */
public class B6603 {
	static int k, arr[], answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		while (true) {
			st = new StringTokenizer(br.readLine());
			k = Integer.parseInt(st.nextToken());
			if (k == 0) {
				break;
			}

			arr = new int[k];
			for (int i = 0; i < k; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			fn(0, 0, "");
			System.out.println();
		}

	}

	public static void fn(int cur, int cnt, String s) {
		if (cnt == 6) {
			System.out.println(s);
			return;
		}

		for (int i = cur; i < k; i++) {
			fn(i + 1, cnt + 1, s + arr[i] + " ");
		}
	}

}
