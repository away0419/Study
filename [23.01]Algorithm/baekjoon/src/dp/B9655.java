package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 돌 게임
 * N : 돌
 * 
 */
public class B9655 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		if (N % 2 == 0) {
			System.out.println("CY");
		} else {
			System.out.println("SK");
		}
	}

}
