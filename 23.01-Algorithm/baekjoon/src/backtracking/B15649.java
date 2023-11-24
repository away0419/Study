package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* N과 M(1)
 * 
 * N : 전체 수
 * M : 조합 갯수
 */
public class B15649 {
	static int N,M;
	static StringBuilder sb;
	static boolean visit[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		sb = new StringBuilder();
		visit = new boolean[N];
		fn("",0);
		System.out.println(sb);
		
	}
	static void fn(String str, int n) {
		if(n == M) {
			sb.append(str).append("\n");
			return;
		}
		for(int i = 0; i < N; i++) {
			if (visit[i]) {
				continue;
			}
			visit[i] = true;
			fn(str + (i + 1) + " ", n + 1);
			visit[i] = false;
		}
	}
}
