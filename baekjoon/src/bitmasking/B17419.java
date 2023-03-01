package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B17419 {

	static int N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		String str = br.readLine();

		int cnt = 0;
		for (int i = 0; i < N; i++) {
			if (str.charAt(N - 1 - i) == '1') {
				cnt++;
			}
		}

		System.out.println(cnt);
	}

}
