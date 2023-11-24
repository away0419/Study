package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 거스름돈
 * N : 거스름돈
 * answer : 최소 동전 수
 */
public class B14916 {
	static int N, answer;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		int c1 = 0, c2 = 0, n = 0;

		c1 = N / 5;
		n = N % 5;

		while (N > 0) {
			if (n % 2 != 0) {
				n += 5;
				c1 -= 1;
				if (c1 < 0) {
					answer = -1;
					break;
				}
			} else {
				c2 = n / 2;
				break;
			}
		}

		if (answer == -1) {
			System.out.println(-1);
		} else {
			System.out.println(c1 + c2);
		}
	}

}
