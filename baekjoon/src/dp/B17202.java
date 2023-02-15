package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 핸드폰 번호 궁합
 * 
 */
public class B17202 {
	static String p1, p2;
	static int arr[][];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		p1 = br.readLine();
		p2 = br.readLine();

		arr = new int[15][16];
		for (int i = 0; i < 8; i++) {
			arr[0][i * 2] = p1.charAt(i)-'0';
			arr[0][i * 2 + 1] = p2.charAt(i)-'0';
		}

		for (int i = 1; i < 15; i++) {
			for (int j = 0; j < 16 - i; j++) {
				int k = arr[i-1][j] + arr[i-1][j + 1];
				k %= 10;
				arr[i][j] = k;
			}
		}

		System.out.println(arr[14][0] + "" + arr[14][1]);

	}
}
