package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 블로그
 * N : 총 날짜
 * X : 연속 일 수
 * cnt : 최대 방문자 수가 나온 총 일
 * max : 최대 방문자 수
 * arr[] : 입력 방문자 수
 * sum[] : 누적합
 */
public class B21921 {
	static int N, X, cnt, max, arr[], sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		arr = new int[N + 1];
		sum = new int[N + 1];
		st = new StringTokenizer(br.readLine());

		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[i] = sum[i - 1] + arr[i];
		}

		for (int i = 0; i <= N - X; i++) {
			if (max < sum[i + X] - sum[i]) {
				max = sum[i + X] - sum[i];
				cnt = 1;
			} else if (max == sum[i + X] - sum[i]) {
				cnt++;
			}
		}

		if (max == 0) {
			System.out.println("SAD");
		} else {
			System.out.println(max);
			System.out.println(cnt);
		}
	}

}
