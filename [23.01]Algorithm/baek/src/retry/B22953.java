package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B22953 {
    static long answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        List<int[]> list = new ArrayList<>();
        int sum = 0;

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            sum += arr[i];
        }

        permutation(0, C, arr, list, N, sum);
        binarySearch(list, K);

        System.out.println(answer);


    }

    public static void binarySearch(List<int[]> list, int K) {
        long start = 1;
        long end = 1000000 * 1000000L;
        answer = end;

        while (start <= end) {
            long mid = (start + end) / 2;
            boolean result = false;

            for (int[] arr : list) {
                result |= check(arr, mid, K);
            }

            if (result) {
                answer = Math.min(answer, mid);
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
    }

    public static boolean check(int[] arr, long time, int K) {
        long make = 0;

        for (int j : arr) {
            make += (time / j);
        }

        return make >= K;
    }

    public static void permutation(int cnt, int C, int[] arr, List<int[]> list, int N, int sum) {
        if (cnt == C || sum == N) {
            int[] copy = new int[N];
            System.arraycopy(arr, 0, copy, 0, N);
            list.add(copy);
            return;
        }

        for (int i = 0; i < N; i++) {
            if (arr[i] > 1) {
                arr[i]--;
                permutation(cnt + 1, C, arr, list, N, sum-1);
                arr[i]++;
            }
        }

    }

}
