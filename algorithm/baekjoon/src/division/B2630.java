package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 색종이 만들기
 * N : 한변의 길이
 * arr[][] : 주어진 종이 배열
 * white : 흰 종이
 * blue : 파란 종이
 */
public class B2630 {
	static int N, arr[][], white, blue;

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

		fn(0, 0, N, N);
		System.out.println(white);
		System.out.println(blue);

	}

	public static void fn(int sx, int sy, int ex, int ey) {
		boolean check = false;
		int k = arr[sx][sy];
		for (int i = sx; i < ex; i++) {
			for (int j = sy; j < ey; j++) {
				if (arr[i][j] != k) {
					check = true;
					break;
				}
			}
			if (check)
				break;
		}

		if (check) {
			fn(sx, sy, (sx + ex) / 2, (sy + ey) / 2);
			fn((sx + ex) / 2, (sy + ey) / 2, ex, ey);
			fn((sx + ex) / 2, sy, ex, (sy + ey) / 2);
			fn(sx, (sy + ey) / 2, (sx + ex) / 2, ey);
		} else {
			if (k == 1) {
				blue++;
			} else {
				white++;
			}
		}

	}

}
