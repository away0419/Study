package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 책정리
 * N : 주어진 수 
 * arr[] : 주어진 배열
 * lis[] : 증가
 * max : 길이
 */
public class B1818 {
	static int N, arr[], lis[], max, cnt;

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
		for (int i = 1; i < N; i++) {
			if (lis[max] < arr[i]) {
				lis[++max] = arr[i];
			} else {
				bs(arr[i]);
			}
		}
		System.out.println(N - (max + 1) + cnt);

	}

	public static void bs(int key) {
		int s = 0;
		int e = max;
		while (s <= e) {
			int m = (s + e) / 2;

			if (lis[m] < key) {
				s = m + 1;
			} else {
				if (lis[m] == key) {
					if (m != max) {
						cnt++;
					}
					return;
				}
				e = m - 1;
			}
		}

		lis[s] = key;
	}
}
