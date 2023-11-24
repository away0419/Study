package twopoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
수들의 합5
N : 자연수
arr[] : 배열
left : 왼쪽 인덱스
right : 오른쪽 인덱스
 */
public class B2018 {
    static int N, left, right, answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        int sum = 0;
        while (left <= N) {
            if (sum + (right + 1) <= N) {
                if (sum + (right + 1) == N) {
                    answer++;
                }
                sum += ++right;
            } else if (sum + (right + 1) > N) {
                sum -= left++;
            }
        }

        System.out.println(answer);
    }


}
