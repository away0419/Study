package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 숫자 카드
 * N : 주어진 수
 * M : 테스트 케이스
 * arr[] 주어진 배열
 */
public class B10815 {
	static int N, M, arr[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		M = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			int k = Integer.parseInt(st.nextToken());
			int res = Arrays.binarySearch(arr, k);
			if (res < 0) {
				sb.append(0).append(" ");
			} else {
				sb.append(1).append(" ");
			}
		}

		System.out.println(sb);
	}
}
