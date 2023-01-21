package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 순서쌍의 곱의 합
 * N : 배열 수
 * arr[] : 주어진 수의 배열
 * sum : 경우의 수 누적 합
 * answer : 총 합
 * */
public class B13900 {
	static int N, arr[];
	static long sum, answer;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = N-1; i >= 0; i--) {
			answer += arr[i] * sum;
			sum += arr[i];
		}
		

		System.out.println(answer);
	}

}
