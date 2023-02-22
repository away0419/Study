package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 222-풀링
 * N : 주어진 수
 * map[][] : 주어진 배열
 * 
 */
public class B17829 {
	static int N, map[][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 1; i < N; i *= 2) {
			int k = N / (i * 2);
			int arr[][] = new int[k][k];

			for (int j = 0; j < k * 2; j += 2) {
				for (int j2 = 0; j2 < k * 2; j2 += 2) {
					int max[] = new int[4];
					max[0] = map[j][j2];
					max[1] = map[j + 1][j2];
					max[2] = map[j][j2 + 1];
					max[3] = map[j + 1][j2 + 1];
					Arrays.sort(max);
					arr[j / 2][j2 / 2] = max[2];
				}
			}
			map = arr;
		}
		System.out.println(map[0][0]);
	}

}
