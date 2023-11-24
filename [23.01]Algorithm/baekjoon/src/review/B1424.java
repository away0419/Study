package review;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1424 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int L = Integer.parseInt(br.readLine());
        int C = Integer.parseInt(br.readLine());
        long answer = Long.MAX_VALUE;
        int max = (C + 1) / (L + 1); // 하나의 cd에 들어 갈 수 있는 최대 곡 수

        for (int i = max; i > 0; i--) {
            if (i % 13 == 0) { // 하나의 cd에 들어가는 곡 수가 13 배수면 패스
                continue;
            }

            int a = N % i; // 하나의 cd에 i만큼 넣을 경우 남는 곡의 갯수
            int cd = 0; // 남은 a만큼의 곡을 담을 cd 갯수 (기본은 0)
            if (a > 0) { // 남은 곡이 있는 경우만 체크
                if (a % 13 == 0 && (a + 1 == i || N / i == 0)) { // 남은 곡 수가 13 배수 && ( 남은 곡수에 +1 한 값이 현재 선택한 곡수와 동일할 경우 || 애초에 곡의 갯수가 cd가 담을 수 있는 수보다 적은 경우)
                    cd = 2;
                } else {
                    cd = 1;
                }
            }
            answer = Math.min(answer, N/i + cd);
        }
        System.out.println(answer);

    }
}
