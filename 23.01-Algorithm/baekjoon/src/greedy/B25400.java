package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 제자리
 * N : 총 갯수
 * 
 */
public class B25400 {
	static int N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		int j = 1;
		int answer = 0;
		for (int i = 0; i < N; i++) {
			int cur = Integer.parseInt(st.nextToken());
			if (cur != j) {
				answer++;
			} else {
				j++;
			}
		}

		System.out.println(answer);

	}

}
