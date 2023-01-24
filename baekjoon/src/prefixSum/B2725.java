package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/* 보이는 점의 개수
 * C : 테스트 케이스
 * N : 좌표 x,y
 */
public class B2725 {
	static int C, N, sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		C = Integer.parseInt(br.readLine());
		sum = new int[1001];
		StringBuilder sb = new StringBuilder();
		sum[1] = 3;
		for (int i = 2; i < 1001; i++) {
			int cnt = 0;
			for (int j = 1; j <= i; j++) {
				int result = eucd(i, j);
				if (result == 1) {
					cnt++;
				}
			}
			sum[i] = sum[i - 1] + cnt * 2;
		}

		while (C-- > 0) {
			N = Integer.parseInt(br.readLine());
			sb.append(sum[N]).append("\n");
		}

		System.out.println(sb);
	}

	public static int eucd(int bn, int sn) {
		int r = bn % sn;

		if (r == 0) {
			return sn;
		} else {
			return eucd(sn, r);
		}

	}

}
