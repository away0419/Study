package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Pen Pineapple Apple Pen
 * N : 총 갯수
 * answer : 최대 갯수
 * arr : 주어진 배열
 */
public class B15881 {
	static int N, answer, arr[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		String str = br.readLine();
		String pattern = "pPAp";
		for (int i = 0; i < N; i++) {
			arr[i] = str.charAt(i);
		}
		int start = 0;
		for (int i = 0; i < N; i++) {
			if (arr[i] == pattern.charAt(start)) {
				if (start == 3) {
					answer++;
					start = 0;
				} else {

					start++;
				}

			} else {
				if (arr[i] == 'p') {
					start = 1;
				} else {
					start = 0;
				}
			}
		}
		System.out.println(answer);
	}

}
