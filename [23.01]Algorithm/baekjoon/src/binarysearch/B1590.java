package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 캠프가는 영식
 * N : 버스 종류
 * T : 도착 시간
 * list : 버스 시간
 * S : 버스 시작
 * I : 간격
 * C : 대수
 */
public class B1590 {
	static int N, T, S, I, C, size;
	static List<Integer> list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		list = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			S = Integer.parseInt(st.nextToken());
			I = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			for (int j = 0; j < C; j++) {
				list.add(S + j * I);
			}
		}
		Collections.sort(list);
		size = list.size() - 1;

		if (list.get(size) < T) {
			System.out.println(-1);
			return;
		}

		System.out.println(bs(T));
	}

	static int bs(int key) {
		int start = 0;
		int end = size;
		while (start <= end) {
			int mid = (start + end) / 2;
			int value = list.get(mid);
			if (value < key) {
				start = mid + 1;
			} else {
				if (value == key) {
					return 0;
				}
				end = mid - 1;
			}
		}

		return list.get(start)-T;

	}
}
