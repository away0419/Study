package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
 * sort 마스터 배지훈의 후계자
 * N : 원소 수
 * arr[] : 배열
 * M : 질문 수
 * 
 */
public class B20551 {
	static int N, M, arr[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			int k = Integer.parseInt(br.readLine());
			arr[i] = k;
		}

		Arrays.sort(arr);
		for (int i = 0; i < M; i++) {
			int k = Integer.parseInt(br.readLine());
			sb.append(bs(0, N - 1, k)).append("\n");
		}
		System.out.println(sb);
	}

	static int bs(int start, int end, int key) {

		while (start <= end) {
			int mid = (start + end) / 2;
			if (arr[mid] < key) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}

		if (start >= N || arr[start] != key)
			return -1;

		return start;

	}

}
