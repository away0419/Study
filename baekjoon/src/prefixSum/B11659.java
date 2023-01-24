package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B11659 {
	static int N, M, sum[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		sum = new int[N+1];
		st = new StringTokenizer(br.readLine());

		StringBuilder sb = new StringBuilder();

		for (int i = 1; i <= N; i++) {
			sum[i] = sum[i - 1]+ Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			sb.append(sum[b] - sum[a-1]).append("\n");
		}
		System.out.println(sb.toString());
	}

}
