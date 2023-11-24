package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 예산
 * N : 지방의 수
 * max : 최대 예산
 * cur : 주어진 현재 예산
 * start : 최소치
 * end : 최대치
 */
public class B2512 {
	static int N, max, arr[], cur, start, end;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			end = Math.max(end, arr[i]);
		}
		cur = Integer.parseInt(br.readLine());

		bs();
		System.out.println(max);

	}

	public static void bs() {

		while (start <= end) {
			int mid = (start + end) / 2;
			int sum = 0;
			for (int i = 0; i < N; i++) {
				if (arr[i] < mid) {
					sum += arr[i];
				} else {
					sum += mid;
				}
			}
			if (sum < cur) {
				start = mid + 1;
			} else {
				if (sum == cur) {
					max = mid;
					return;
				}
				end = mid - 1;
			}
		}

		max = start-1;
	}
}
