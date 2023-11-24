package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 이장님 초대
 * N : 묘목 수
 * arr[] : 걸리는 날짜 배열
 */
public class B9237 {
	static int N, arr[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		int answer = 0;
		for (int i = N - 1; i >= 0; i--) {
			answer = Math.max(arr[i] + (N - i), answer);
		}
		System.out.println(answer + 1);
	}

}
