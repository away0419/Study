package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 수들의 합
 * S : N개의 자연수로 만든 합
 */
public class B1789 {
	static long S, N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = Long.parseLong(br.readLine());

		long i = 1;
		while (S > 0) {
			if (S - i >= i + 1) {
				S -= i++;
				N++;
			} else {
				S = 0;
				N++;
			}
		}

		System.out.println(N);
	}

}
