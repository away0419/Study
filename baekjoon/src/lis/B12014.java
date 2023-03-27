package lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
T : 테스트 케이스
N : 총 일
K : 사려는 날짜 수
arr[] : 주어지는 배열
dp[] : 최장 증가 수열 담을 배열
 */

public class B12014 {
    static int N, K, arr[], dp[], T;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= T; tc++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            arr = new int[N];
            dp = new int[N];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            int max = 0;
            dp[0] = arr[0];
            for (int i = 1; i < N; i++) {
                if (dp[max] < arr[i]) {
                    dp[++max] = arr[i];
                } else {
                    binarySearch(0, max, arr[i]);
                }
            }
            sb.append("Case #").append(tc).append("\n");
            if (max + 1 >= K) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void binarySearch(int start, int end, int value) {
        while (start <= end) {
            int mid = (start + end) / 2;

            if (dp[mid] < value) {
                start = mid + 1;
            } else {
                if (dp[mid] == value) {
                    return;
                }
                end = mid - 1;
            }
        }
        dp[start] = value;
    }
}
