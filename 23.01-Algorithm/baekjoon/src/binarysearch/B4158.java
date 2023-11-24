package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * CD
 * N, M : 주어진수
 * arr[] : 배열
 */
public class B4158 {
	static int N, M, answer;
	static Set<Integer> set;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		while (true) {
			if (M == 0 && N == 0) {
				break;
			}
			set = new HashSet<>();
			answer = 0;
			for (int i = 0; i < N + M; i++) {
				if (!set.add(Integer.parseInt(br.readLine()))) {
					answer++;
				}
			}
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			System.out.println(answer);
		}
	}

}
