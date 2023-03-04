package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 꼬인 전깃줄
 * N : 수
 * arr[] : 주어진 수
 * lis[] : 최장
 * max : 길이
 */
public class B1365 {
	static int N, arr[], lis[], max;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		arr = new int[N];
		lis = new int[N];

		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		lis[0] = arr[0];
		for (int i = 0; i < N; i++) {
			if (lis[max] < arr[i]) {
				lis[++max] = arr[i];
			} else {
				bs(arr[i]);
			}
		}

		System.out.println(N - (max + 1));
	}

	public static void bs(int key) {
		int start = 0;
		int end = max;

		while (start <= end) {
			int mid = (start + end) / 2;

			if (lis[mid] < key) {
				start = mid + 1;
			} else {
				if (lis[mid] == key) {
					return;
				}
				end = mid - 1;
			}
		}
		lis[start] = key;
	}
}
