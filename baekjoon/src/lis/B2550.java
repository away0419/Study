package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
 * 전구
 * N : 전구 수
 * arr[] : 주어진 배열
 * lis[] : 증가
 * answer : 갯수
 * list[] : 가장 긴 
 */
public class B2550 {
	static int N, arr[], lis[], path[], answer;
	static Map<Integer, Integer> map, map2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringTokenizer st2 = new StringTokenizer(br.readLine());
		arr = new int[N];
		lis = new int[N];
		path = new int[N];
		map = new HashMap<>();
		map2 = new HashMap<>();

		for (int i = 0; i < N; i++) {
			int k = Integer.parseInt(st2.nextToken());
			map.put(k, i);
			map2.put(i, k);
		}

		for (int i = 0; i < N; i++) {
			int k = Integer.parseInt(st.nextToken());
			arr[i] = map.get(k);
		}

		lis[0] = arr[0];
		for (int i = 1; i < N; i++) {
			if (lis[answer] < arr[i]) {
				lis[++answer] = arr[i];
				path[i] = answer;
			} else {
				bs(arr[i], i);
			}
		}

		System.out.println(answer + 1);

		int s[] = new int[answer + 1];
		for (int i = N - 1; i >= 0; i--) {
			if (path[i] == answer) {
				s[answer--] = map2.get(arr[i]);
			}
		}

		Arrays.sort(s);
		for (int i : s) {
			System.out.print(i + " ");
		}
	}

	public static void bs(int key, int index) {
		int start = 0;
		int end = answer;

		while (start <= end) {
			int mid = (start + end) / 2;
			if (lis[mid] < key) {
				start = mid + 1;
			} else {
				if (lis[mid] == key) {
					path[index] = mid;
					return;
				}
				end = mid - 1;
			}
		}

		path[index] = start;
		lis[start] = key;
	}
}
