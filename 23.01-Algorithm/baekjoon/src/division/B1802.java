package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
종이 접기
T : 테스트 케이스
str : 주어진 종이 상태
 */
public class B1802 {
    static int T;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            String str = br.readLine();
            int len = str.length();
            int N = 1;
            boolean check = true;
            while (Math.pow(2, N) - 1 < len) {
                int left = (int) Math.pow(2, N) - 1;
                int right = len - (int) Math.pow(2, N);

                if (left != right) {
                    if (!fn(left, left, str) || !fn(right, left, str)) {
                        check = false;
                        break;
                    }
                } else {
                    if (!fn(left, left, str)) {
                        check = false;
                        break;
                    }
                }
                N++;

            }

            if (check) {
                sb.append("YES").append("\n");
            } else {
                sb.append("NO").append("\n");
            }

        }

        System.out.println(sb);
    }

    public static boolean fn(int mid, int n, String str) {
        for (int i = 1; i <= n; i++) {
            if (str.charAt(mid - i) == str.charAt(mid + i)) {
                return false;
            }
        }
        return true;
    }
}
