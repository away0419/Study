package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
 * DNA
 * N : DNA 수
 * M : 문자열 길이
 * list : 가장 작은 합을 가진 문장의 리스트
 * min : 가장 작은 합의 수
 */
public class B1969 {
	static int N, M, min = Integer.MAX_VALUE;
	static List<String> list = new ArrayList<>();
	static String[] arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new String[N];
		String answer = "";
		int k = 0;
		char[] carr = new char[] { 'A', 'C', 'G', 'T' };
		for (int i = 0; i < N; i++) {
			arr[i] = br.readLine();
		}

		for (int i = 0; i < M; i++) {
			int cnt[] = new int[4];
			for (int j = 0; j < N; j++) {
				if (arr[j].charAt(i) == 'A') {
					cnt[0]++;
				} else if (arr[j].charAt(i) == 'C') {
					cnt[1]++;
				} else if (arr[j].charAt(i) == 'G') {
					cnt[2]++;
				} else if (arr[j].charAt(i) == 'T') {
					cnt[3]++;
				}
			}
			int max = 0;
			int idx = -1;
			int mink = 0;
			for (int j = 0; j < 4; j++) {
				if (max < cnt[j]) {
					max = cnt[j];
					idx = j;
					mink = N - cnt[j];
				} else if (max == cnt[j]) {
					if (idx > j) {
						idx = j;
					}
				}
			}

			answer += carr[idx];
			k += mink;
		}

		System.out.println(answer);
		System.out.println(k);

	}
}