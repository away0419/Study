package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/*
 * S : 주어지는 글
 * K : 최소 답
 */
public class B19564 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		String str = "abcdefghijklmnopqrstuvwxyz";
		int K = 0;
		
		for (int i = 0; i < s.length(); i += 0) {
			for (int j = 0; j < str.length(); j++) {
				if (str.charAt(j) == s.charAt(i)) {
					i++;
					if (i == s.length()) {
						break;
					}
				}
			}
			K++;
		}
		
		
		System.out.println(K);
	}

}
