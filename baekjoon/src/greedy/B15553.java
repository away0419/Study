package greedy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 난로
 * N : 친구의 수
 * K : 성냥의 갯수
 * T : 친구의 도착 시간
 * arr[] : 친구 사이 텀 배열
 * answer : 최소 시간
 * 
 * 1. 친구가 오면 항상 켜야한다.
 * 2. K 만큼만 킬 수 있다.
 * 3. 친구 없으면 끈다.
 * 4. K가 부족하면 틀어놔야한다
 * 
 */
public class B15553 {
	static int N, K, T, answer, arr[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N - 1];
		if (K == N) {
			System.out.println(K);

		} else {

			int start = Integer.parseInt(br.readLine());
			for (int i = 0; i < N - 1; i++) {
				int next = Integer.parseInt(br.readLine());
				arr[i] = next - start;
				start = next;
			}
			answer = K;
			Arrays.sort(arr);
			for (int i = 0; i < N - K; i++) {
				answer += arr[i];
			}
			System.out.println(answer);
		}

	}

}
