package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * 숫자 카드 2
 * N, M : 주어진 수
 * set : 배열
 */
public class B10816 {
	static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < N; i++) {
			int k = Integer.parseInt(st.nextToken());
			map.put(k, map.getOrDefault(k, 0) + 1);
		}

		M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			int k = Integer.parseInt(st.nextToken());
			if (!map.containsKey(k)) {
				sb.append(0).append(" ");
			} else {
				sb.append(map.get(k)).append(" ");
			}
		}
		System.out.println(sb);
	}
}
