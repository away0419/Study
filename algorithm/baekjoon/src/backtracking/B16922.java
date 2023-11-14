package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/* 로마 숫자 만들기
 * N : 총 로마 글자 수
 * arr[4] = 로마 숫자 배열
 */
public class B16922 {
	static int N, arr[];
	static Set<Integer> set = new HashSet<>();

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[] { 1, 5, 10, 50 };
		fn(0, 0, 0);
		System.out.println(set.size());
	}

	public static void fn(int cur, int cnt, int sum) {
		if (cnt == N) {
			set.add(sum);
			return;
		}

		for (int i = cur; i < 4; i++) {
			fn(i, cnt + 1, sum + arr[i]);
		}
	}

}
