package twopoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
K보다 큰 구간
 */
public class B14246 {
    static int N;
    static int[] arr;
    static int K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        long answer = 0;
        int start = 0;
        int end = 0;
        long sum = arr[0];
        while (start < N && end < N) {
            if (sum <= K) {
                if (end == N-1) {
                    break;
                }
                sum += arr[++end];
            } else {
                answer += N - end;
                sum -= arr[start++];
            }
        }

        System.out.println(answer);

    }
}
