package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 특별상이라도 받고 싶어
 * N : 행렬 수
 * arr[][] : 번호
 * 1. 4명 중 뒤에서 작은 한명이 후보
 * 
 */
public class B24460 {
	static int N, arr[][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int k = 1;
		while (k < N) {

			for (int i = 0; i < N; i += k * 2) {
				for (int j = 0; j < N; j += k * 2) {
					int[] temp = new int[4];
					temp[0] = arr[i][j];
					temp[1] = arr[i + k][j];
					temp[2] = arr[i][j + k];
					temp[3] = arr[i + k][j + k];
					Arrays.sort(temp);
					arr[i][j] = temp[1];
				}
			}

			k *= 2;

		}
		System.out.println(arr[0][0]);

	}

}
