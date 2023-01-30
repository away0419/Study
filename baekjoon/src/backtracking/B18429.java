package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 근손실
 * N : 날짜
 * K : 하루 당 빠지는 kg
 * arr[] : 운동 키트 배열
 * visit[] : 방문 
 */
public class B18429 {
	static int N, K, arr[], answer;
	static boolean visit[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N];
		visit = new boolean[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		fn(0,0);
		System.out.println(answer);
	}

	public static void fn(int cnt, int sum) {
		if (cnt == N) {
			answer++;
			return;
		}

		for (int i = 0; i < N; i++) {
			int s = sum + arr[i] - K;
			if (visit[i] || s < 0) {
				continue;
			}
			visit[i] = true;
			fn(cnt + 1, s);
			visit[i] = false;
		}
	}
}
