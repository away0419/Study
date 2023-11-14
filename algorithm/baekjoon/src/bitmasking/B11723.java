package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B11723 {

	static int N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(br.readLine());
		int k = 0;
		for (int i = 0; i < 20; i++) {
			k += 1 << i;
		}
		int s = 1 << 20;

		while (N-- > 0) {
			st = new StringTokenizer(br.readLine());

			String str = st.nextToken();
			int num = 0;
			switch (str) {
			case "all":
				s = k;
				break;
			case "empty":
				s &= 0;
				break;
			case "add":
				num = Integer.parseInt(st.nextToken()) - 1;
				s |= 1 << num;
				break;
			case "remove":
				num = Integer.parseInt(st.nextToken()) - 1;
				s &= ~(1 << num);
				break;
			case "check":
				num = Integer.parseInt(st.nextToken()) - 1;

				if (((s >> num) & 1) == 1) {
					sb.append(1).append("\n");
				} else {
					sb.append(0).append("\n");
				}

				break;
			case "toggle":
				num = Integer.parseInt(st.nextToken()) - 1;
				s ^= (1 << num);
				break;
			default:
				break;
			}

		}
		System.out.println(sb);
	}

}
