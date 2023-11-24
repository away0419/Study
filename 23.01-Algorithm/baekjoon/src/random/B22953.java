package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class B22953 {
    static long answer;
    static List<long[]> list;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        long C = Long.parseLong(st.nextToken());
        long[] cost = new long[N];
        list = new ArrayList<>();
        answer = Long.MAX_VALUE;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            cost[i] = Long.parseLong(st.nextToken());
        }
        Arrays.sort(cost);

        combination(0, 0, N, C, cost);
        binarySearch(0L, cost[N - 1] * K, N, K);

        System.out.println(answer);
    }

    public static void binarySearch(long start, long end, long N, long K) {
        while (start <= end) {
            long mid = (start + end) / 2;

            long cookCnt = 0;
            for (long[] longs : list) {
                long max = 0;
                for (int i = 0; i < N; i++) {
                    max += (mid / longs[i]);
                }
                cookCnt = Math.max(max, cookCnt);
            }
            if (cookCnt < K) {
                start = mid+1;
            } else {
                answer = Math.min(answer, mid);
                end = mid-1;
            }
        }
    }

    public static void combination(int cur, long cnt, int N, long C, long[] cost) {
        if (cnt == C) {
            long[] copy = new long[N];
            System.arraycopy(cost, 0, copy, 0, N);
            list.add(copy);

            return;
        }

        for (int i = cur; i < N; i++) {
            if (cost[i] > 1) {
                cost[i]--;
                combination(i, cnt + 1, N, C, cost);
                cost[i]++;
            }else{
                combination(i, cnt + 1, N, C, cost);
            }
        }
    }
}
