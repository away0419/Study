package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 인간 컴퓨터 상호작용
 * S : 문자열
 * q : 테스트 케이스
 * ch : 주어진 소문자
 * l : 시작
 * r : 끝
 * sum[][] : [소문자 종류][S길이] 누적합
 * arr[] : S의 각 문자를 int로 변환한 배열
 * 
 */
public class B16139 {
	static int q, ch, arr[], sum[][], l, r;
	static String S;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = br.readLine();
		q = Integer.parseInt(br.readLine());
		arr = new int[S.length() + 1];
		sum = new int[26][S.length() + 1];
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < S.length(); i++) {
			arr[i + 1] = S.charAt(i) - 'a';
		}

		for (int i = 0; i < 26; i++) {
			for (int j = 1; j <= S.length(); j++) {
				sum[i][j] = sum[i][j - 1];
				if (arr[j] == i) {
					sum[i][j]++;
				}
			}
		}

		while (q-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			ch = st.nextToken().charAt(0) - 'a';
			l = Integer.parseInt(st.nextToken());
			r = Integer.parseInt(st.nextToken()) + 1;

			sb.append(sum[ch][r] - sum[ch][l]).append("\n");
		}

		System.out.println(sb);

	}

}
