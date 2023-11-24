package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 가위 바위 보
 * N : 총 친구 수
 * R : 총 라운드
 * arr[][] : 라운드 별 친구들이 낸 갈람보 수
 * answer : 최고 점수
 * sum : 실제 점수
 * 
 */
public class B2930 {
	static int N, R, arr[][], answer, sum;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		String str = br.readLine();
		arr = new int[N][4];
		for (int i = 0; i < N; i++) {
			if (str.charAt(i) == 'S') {
				arr[i][0] = 1;
			} else if (str.charAt(i) == 'R') {
				arr[i][0] = 2;
			} else if (str.charAt(i) == 'P') {
				arr[i][0] = 3;
			}
		}

		R = Integer.parseInt(br.readLine());
		for (int i = 0; i < R; i++) {
			str = br.readLine();
			for (int j = 0; j < N; j++) {
				if (str.charAt(j) == 'S') {
					arr[j][1] += 1;
				} else if (str.charAt(j) == 'R') {
					arr[j][2] += 1;
				} else if (str.charAt(j) == 'P') {
					arr[j][3] += 1;
				}
			}
		}

		for (int i = 0; i < N; i++) {
			int sang = arr[i][0];
			int max = 0;
			if (sang == 1) {
				sum += arr[i][3] * 2 + arr[i][1];
			} else if (sang == 2) {
				sum += arr[i][1] * 2 + arr[i][2];
			} else if (sang == 3) {
				sum += arr[i][2] * 2 + arr[i][3];
			}

			int k1 = arr[i][2] * 2 + arr[i][3];
			int k2 = arr[i][1] * 2 + arr[i][2];
			int k3 = arr[i][3] * 2 + arr[i][1];
			max = Math.max(k1, Math.max(k2, k3));

			answer += max;
		}

		System.out.println(sum);
		System.out.println(answer);

	}

}
