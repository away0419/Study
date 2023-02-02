package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 한조
 * N : 배열 수 
 * arr[] : 배열
 * max : 최대 값
 */
public class B14959 {
	static int N, arr[], cnt, answer, cur;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			if (arr[i] > cur) {
				answer = Math.max(answer, cnt);
				cnt = 0;
				cur = arr[i];
			} else {
				cnt++;
			}
		}

		answer = Math.max(answer, cnt);
		System.out.println(answer);

	}

}
