package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 브리징 시그널
 * T : 테스트 케이스 수
 * N : 총 갯수
 * arr[] : 주어진 배열
 * lis[] : 최장
 * max : 길이
 */
public class B3066 {
	static int T, N, arr[], lis[], max;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		while (T-- > 0) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N];
			lis = new int[N];
			max = 0;

			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(br.readLine());
			}

			lis[0] = arr[0];

			for (int i = 0; i < N; i++) {
				if (lis[max] < arr[i]) {
					lis[++max] = arr[i];
				} else {
					bs(arr[i]);
				}
			}

			System.out.println(max+1);
		}

	}

	public static void bs(int k) {
		int s = 0;
		int e = max;

		while (s <= e) {
			int m = (s + e) / 2;
			if (lis[m] < k) {
				s = m + 1;
			} else {
				if (lis[m] == k) {
					return;
				}
				e = m - 1;
			}
		}

		lis[s] = k;
	}
}
