package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 배고픈 아리의 샌드위치 구매하기
 * S : 가격
 * M : 쿠키가 가진 금액
 * 
 */
public class B25166 {
	static int S, M, money[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		if (S < 1024) {
			System.out.println("No thanks");
		} else {
			int needMoney = S - 1023;
			if ((needMoney & M) == needMoney) {
				System.out.println("Thanks");
			} else {
				System.out.println("Impossible");
			}

		}
	}

}
