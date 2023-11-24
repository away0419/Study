package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 민균이의 계략
 * N : 주어진 수
 * arr : 주어진 배열
 * lis : dp 저장
 */
public class B11568 {
	static int N, arr[], lis[], answer;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		lis = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		lis[0] = arr[0];

		for (int i = 1; i < N; i++) {
			if (lis[answer] < arr[i]) {
				lis[++answer] = arr[i];
			} else {
				fn(arr[i]);
			}
		}
		System.out.println(answer+1);
	}

	public static void fn(int value) {
		int start = 0;
		int end = answer;

		while (start <= end) {
			int mid = (start + end) / 2;

			if (value < lis[mid]) {
				end = mid - 1;
			} else {
				if (value == lis[mid]) {
					lis[mid] = value;
					return;
				}
				start = mid + 1;
			}
		}
		lis[start] = value;
	}
}
