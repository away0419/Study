package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 종이의 개수
 * N : 행렬
 * map[][] : 주어진 종이
 * a,b,c : -1, 0, 1 갯수
 * 
 */
public class B1780 {
	static int N, map[][], a, b, c;

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

		fn(0, 0, N, N);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}

	public static void fn(int sx, int sy, int ex, int ey) {
		if (check(sx, sy, ex, ey)) {
			if (map[sx][sy] == -1) {
				a++;
			} else if (map[sx][sy] == 0) {
				b++;
			} else {
				c++;
			}
			return;
		}

		int k = (ex - sx) / 3;

		int x1 = sx + k;
		int y1 = sy + k;
		int x2 = sx + (k * 2);
		int y2 = sy + (k * 2);

		fn(sx, sy, x1, y1);
		fn(sx, y1, x1, y2);
		fn(sx, y2, x1, ey);

		fn(x1, sy, x2, y1);
		fn(x1, y1, x2, y2);
		fn(x1, y2, x2, ey);

		fn(x2, sy, ex, y1);
		fn(x2, y1, ex, y2);
		fn(x2, y2, ex, ey);

	}

	public static boolean check(int sx, int sy, int ex, int ey) {
		int k = map[sx][sy];
		for (int i = sx; i < ex; i++) {
			for (int j = sy; j < ey; j++) {
				if (k != map[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

}
