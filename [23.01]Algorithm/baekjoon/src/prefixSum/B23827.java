package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 수열
 * 
 * N : 길이
 * s1[] : 1-b 사이에 있는 무작위 2개의 수 뽑아 곱한 값을 모두 더한 수의 배열
 * sum : 지금 까지 더한 수 % 1 000 000 007
 */
public class B23827 {
	static int N;
	static long s1[], sum;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		s1 = new long[N + 1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i < N + 1; i++) {
			int a = Integer.parseInt(st.nextToken());
			s1[i] = (s1[i - 1] + sum * a) % 1000000007;
			sum = (sum + a) % 1000000007;
		}

		System.out.println(s1[N]);
	}

}
