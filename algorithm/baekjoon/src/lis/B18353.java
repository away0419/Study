package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B18353 {
	static int N, answer;
	static int[] arr, dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		dp = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());

		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		dp[0] = arr[0];

		for (int i = 1; i < N; i++) {
			if (dp[answer] > arr[i]) {
				dp[++answer] = arr[i];
			} else {
				fn(arr[i]);
			}
		}

		System.out.println(N - (answer+1));
	}

	static void fn(int num) {
		int start = 0;
		int end = answer;
		while (start <= end) {
			int mid = (start + end) / 2;

			if (dp[mid] > num) {
				start = mid + 1;
			} else {
				if (dp[mid] == num) {
					return;
				}
				end = mid - 1;
			}
		}

		dp[start] = num;

	}

}
