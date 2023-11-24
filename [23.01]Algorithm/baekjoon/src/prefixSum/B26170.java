package prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * N : 길이
 * arr[] : 주어진 배열
 * sum[] : 누적 합
 * 
 */
public class B26170 {
	static int N, arr[], sum[], answer;
	static boolean prime[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		arr = new int[N + 1];
		sum = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum[i] = sum[i - 1] + arr[i];
		}

		int max = Math.max(N, sum[N]);
		prime = new boolean[max + 1];
		check(max);
		fn();
		System.out.println(answer);

	}

	public static void check(int max) {
		prime[0] = prime[1] = true;
		for (int i = 2; i * i <= max; i++) {
			if (!prime[i]) {
				for (int j = i * i; j <= max; j += i) {
					prime[j] = true;
				}
			}
		}
	}

	public static void fn() {
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j <= N; j++) {
				int s = sum[j] - sum[i];
				int diff = j - i;
				if (!prime[s] && !prime[diff]) {
					answer++;
				}
			}
		}
	}

}
