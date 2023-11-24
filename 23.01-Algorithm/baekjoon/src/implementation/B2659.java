package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
십자카드 문제

 */
public class B2659 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int target = 0;

        for (int i = 0; i < 4; i++) {
            int k = Integer.parseInt(st.nextToken());
            target *= 10;
            target += k;
        }

        target = make(target);

        int answer = fn(target);

        System.out.println(answer);
    }

    public static int fn(int target) {
        Set<Integer> set = new HashSet<>();
        int cnt = 1;

        for (int i = 1111; i <= 9999; i++) {
            if (!check(i)) {
                continue;
            }

            int min = make(i);

            if(!set.add(min)){
                continue;
            }

            if (target == min) {
                break;
            }

            cnt++;
        }
        return cnt;
    }

    public static boolean check(int no) {
        for (int i = 0; i < 4; i++) {
            if (no % 10 == 0) {
                return false;
            }
            no /= 10;
        }
        return true;
    }

    public static int make(int no) {
        String str = String.valueOf(no);
        int min = no;

        for (int i = 0; i < 4; i++) {
            str = str.charAt(3) + str.substring(0, 3);
            min = Math.min(min, Integer.parseInt(str));
        }

        return min;
    }
}
