package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 가장 긴 증가하는 부분 수열 3
 */
public class B12738 {
	static int N, lis[], arr[], answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		lis = new int[N];
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		lis[0] = arr[0];

		for (int i = 1; i < N; i++) {
			if (lis[answer] < arr[i]) {
				lis[++answer] = arr[i];
			} else {
				bs(arr[i]);
			}
		}

		System.out.println(answer + 1);
	}

	public static void bs(int key) {
		int start = 0;
		int end = answer;

		while (start <= end) {
			int mid = (start + end) / 2;
			if (lis[mid] > key) {
				end = mid - 1;
			} else {
				if (lis[mid] == key) {
					return;
				}
				start = mid + 1;
			}
		}

		lis[start] = key;
	}
}
