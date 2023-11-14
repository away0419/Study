package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 크리 문자열
 * 
 * S : 문자열
 * arr[] : 해당 문자열의 숫자로 변환한 배열
 * sum[] : 누적합
 */
public class B11059 {
	static String S;
	static int arr[], sum[], max;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = br.readLine();
		int l = S.length();
		arr = new int[l + 1];
		sum = new int[l + 1];

		for (int i = 1; i <= l; i++) {
			arr[i] = S.charAt(i - 1) - '0';
			sum[i] = sum[i - 1] + arr[i];
		}

		for (int i = 0; i < l; i++) {
			for (int j = i + 1; j <= l; j++) {
				if ((j - i) % 2 == 1)
					continue;
				int a = sum[((j-i)/2)+i] - sum[i];
				int b = sum[j] - sum[((j-i)/2)+i];
				if (a == b) {
					max = Math.max(max, j - i);
				}
			}
		}
		System.out.println(max);
	}
}
