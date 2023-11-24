package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
랜선 자르기
K : 랜선 개수
N : 최소 필요 랜선 갯수
arr[] : 주어진 랜선 길이 배열
 */
public class B1654 {
    static int K, N, arr[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        arr = new int[K];
        long start = 1;
        long end = 0;
        for (int i = 0; i < K; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            end = Math.max(end, arr[i]);
        }

        while (start <= end) {
            long mid = (start + end) / 2;
            int cnt = 0;
            for (int i = 0; i < K; i++) {
                cnt += arr[i] / mid;
            }

            if (cnt < N) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        System.out.println(start - 1);
    }


}
