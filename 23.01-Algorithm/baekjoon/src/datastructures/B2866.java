package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
R : 행
C : 열
arr[] 주어진 문자열 배열
 */
public class B2866 {
    static int R, C, answer;
    static String[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        arr = new String[R];
        for (int i = 0; i < R; i++) {
            arr[i] = br.readLine();
        }
        fn();

        System.out.println(answer);
    }

    public static void fn() {
        int start = 0;
        int end = R - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            boolean check = check(mid);
            if (check) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        answer = (end == R) ? end - 1 : end;
    }

    public static boolean check(int idx) {
        Set<String> set = new HashSet<>();
        for (int j = 0; j < C; j++) {
            char[] charArr = new char[R - idx];
            for (int i = idx; i < R; i++) {
                charArr[i - idx] = arr[i].charAt(j);
            }
            if (!set.add(String.valueOf(charArr))) {
                return true;
            }
        }
        return false;
    }
}
