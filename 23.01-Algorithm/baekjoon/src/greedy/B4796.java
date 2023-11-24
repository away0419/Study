package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 캠핑
 * P : 연속하는 일
 * L : 사용 가능 일 수
 * V : 휴가 수
 */
public class B4796 {
	static long P, L, V;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int i = 1;
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			L = Long.parseLong(st.nextToken());
			P = Long.parseLong(st.nextToken());
			V = Long.parseLong(st.nextToken());
			if (P == 0)
				break;
			long j = V / P;
			long answer = Math.min(V % P,L);
			answer += j * L;
			sb.append("Case " + i + ": " + answer + "\n");
			i++;
		}
		System.out.println(sb);
	}

}
