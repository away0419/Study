package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 청정수열
 * N : 주어진 수
 */
public class B25176 {
	static int N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		int answer = 1;
		for (int i = 1; i <= N; i++) {
			answer *= i;
		}

		System.out.println(answer);
	}

}
