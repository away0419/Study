package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 어두운 굴다리
 * 
 * N : 굴다리 길이
 * M : 가로등 갯수
 * min : 최소 높이
 * arr[] : 위치
 */
public class B172266 {
	static int N, M, arr[], max;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		int start = Integer.parseInt(st.nextToken());
		if (start != 0) {
			max = start;
		}

		for (int i = 0; i < M - 1; i++) {
			int k = Integer.parseInt(st.nextToken());
			int mid = (k - start + 1) / 2;
			max = Math.max(max, mid);
			start = k;
		}

		if (start == N) {
			int mid = (N - start + 1) / 2;
			max = Math.max(max, mid);
		} else {
			max = Math.max(max, N - start);
		}

		System.out.println(max);
	}

}
