package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 카우버거
 * B : 주문한 버거
 * C : 사이드 
 * D : 음료수
 * answer1 : 할인 적용 되기 전
 * answer2 : 할인 적용 최소 가격
 */
public class B15720 {
	static int B, C, D, answer1, answer2, arr[][], arr2[], min;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		B = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		arr = new int[3][];
		arr[0] = new int[B];
		arr[1] = new int[C];
		arr[2] = new int[D];
		arr2 = new int[] { B, C, D };
		min = Math.min(B, Math.min(D, C));
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			int j = 0;
			while (st.hasMoreTokens()) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				answer1 += arr[i][j++];
			}
		}

		Arrays.sort(arr[0]);
		Arrays.sort(arr[1]);
		Arrays.sort(arr[2]);

		for (int i = 0; i < min; i++) {
			for (int j = 0; j < 3; j++) {
				answer2 += arr[j][(arr2[j] - i - 1)];
			}
		}

		answer2 = answer2 / 100 * 10;

		System.out.println(answer1);
		System.out.println(answer1 - answer2);

	}

}
