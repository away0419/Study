package binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
나무 자르기
M : 필요한 나무 길이
H : 절단기 높이
arr[] : 주어지는 나무 높이
 */
public class B2805 {
    static int N, M, H, arr[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        int max = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            max = Math.max(max, arr[i]);
        }
        int answer = fn(max, 0);
        System.out.println(answer);
    }

    public static int fn(int start, int end) {

        while (start <= end) {
            int mid = (int) (((long) start + end) / 2);
            long sum = 0;
            for (int value :
                    arr) {
                if (value > mid) {
                    sum += value - mid;
                }
            }

            if (sum < M) {
                end = mid - 1;
            } else {
                if (sum == M) {
                    return mid;
                }
                start = mid + 1;
            }
        }

        return end;
    }
}
