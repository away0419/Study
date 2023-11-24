package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 눈덩이 굴리기
 * N : 앞마당 길이 1부터 N까지
 * M : 눈덩이 굴린 시간 시작위치는 0
 * arr[] : 눈 높이
 * answer : 가장 큰 눈덩이
 */
public class B21735 {
	static int N, M, arr[], answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N+2];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		fn(0, 0, 1);
		System.out.println(answer);
	}

	public static void fn(int cur, int cnt, int sum) {
		if (cnt == M || cur>=N) {
			answer = Math.max(answer, sum);
			return;
		}
		
		fn(cur+1,cnt+1,sum+arr[cur+1]);
		fn(cur+2,cnt+1,sum/2+arr[cur+2]);
	}

}
