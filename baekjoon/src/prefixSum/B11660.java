package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 구간 합 구하기 5
 * N : 행 렬
 * M : 테스트 케이스
 * arr[][] : 입력 행렬
 * sum[][] : 누적 합
 * 
 */
public class B11660 {
	static int N, M, arr[][], sum[][];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N + 1][N + 1];
		sum = new int[N + 1][N + 1];
		StringBuilder sb = new StringBuilder();

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		init();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());

			int s = sum[x2][y2] + sum[x1 - 1][y1 - 1] - sum[x1 - 1][y2] - sum[x2][y1 - 1];
			sb.append(s).append("\n");
		}

		System.out.println(sb);

	}

	public static void init() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				sum[i][j] = sum[i - 1][j] - sum[i - 1][j - 1] + sum[i][j - 1] + arr[i][j];
			}
		}
	}

}
