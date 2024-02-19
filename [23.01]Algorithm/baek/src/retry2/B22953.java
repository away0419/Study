package retry2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B22953 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int[] times = new int[N];
        List<int[]> list = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            times[i] = Integer.parseInt(st.nextToken());
        }

        combination(0, N, C, 0, times, list);
        long answer = binarySearch(K, list);

        System.out.println(answer);
    }

    public static long binarySearch(int K, List<int[]> list) {
        long start = 1;
        long end = 1000000L * 1000000L;
        int listSize = list.size();

        while (start <= end) {
            long mid = (start + end) / 2;
            long finishedDishCnt = 0L;

            for (int i = 0; i < listSize; i++) {
                long cnt = 0L;
                for (int time :
                        list.get(i)) {
                    cnt += mid / time;
                }
                finishedDishCnt = Math.max(cnt,finishedDishCnt);
            }

            if (finishedDishCnt < K) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return end+1;

    }


    public static void combination(int cur, int N, int C, int cnt, int[] times, List<int[]> list) {
        if (cnt == C) {
            int[] copyTimes = new int[N];
            System.arraycopy(times, 0, copyTimes, 0, N);
            list.add(copyTimes);
            return;
        }

        for (int i = cur; i < N; i++) {
            if (times[i] > 1) {
                times[i]--;
                combination(i, N, C, cnt + 1, times, list);
                times[i]++;
            }else{
                combination(i, N, C, cnt+1, times, list);
            }
        }

    }

}
