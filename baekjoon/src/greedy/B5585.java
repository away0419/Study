package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 거스름돈
 * arr[] : 거스름돈 
 * cost : 돈
 */
public class B5585 {
	static int cost, arr[], answer;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		cost = 1000 - Integer.parseInt(br.readLine());
		arr = new int[] { 500, 100, 50, 10, 5, 1 };

		int i = 0;
		while (cost > 0) {
			int j = cost / arr[i];
			answer += j;
			cost %= arr[i++];
		}

		System.out.println(answer);

	}

}
