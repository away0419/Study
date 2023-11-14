package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 
 * 영재의 시험
 * answer[] : 10 문제 정답
 * score : 합계 점수
 * cun : 연속
 */
public class B19949 {
	static int arr[], answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		arr = new int[10];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 10; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		fn(0, 0, 0, 0);
		System.out.println(answer);

	}

	public static void fn(int pre, int cnt, int cun, int score) {
		if (cnt == 10) {
			if (score > 4)
				answer++;
			return;
		}

		for (int i = 1; i <= 5; i++) {
			if (i == pre) {
				if (cun + 1 < 3) {
					if (arr[cnt] == i)
						fn(i, cnt + 1, cun + 1, score + 1);
					else
						fn(i, cnt + 1, cun + 1, score);
				}
			} else {
				if (arr[cnt] == i)
					fn(i, cnt + 1, 1, score + 1);
				else
					fn(i, cnt + 1, 1, score);
			}
		}
	}

}
