package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 전자레인지
 * arr[] : 초
 * cnt[] : 갯수
 * S : 초
 * 
 */
public class B10162 {
	static int arr[], cnt[], S;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		arr = new int[] { 300, 60, 10 };
		cnt = new int[3];
		S = Integer.parseInt(br.readLine());

		int i = 0;
		while (i < 3 && S > 0) {
			cnt[i] = S / arr[i];
			S %= arr[i++];
		}

		if (S != 0) {
			System.out.println(-1);
		} else {
			for (int j : cnt) {
				System.out.print(j + " ");
			}
		}

	}
}
