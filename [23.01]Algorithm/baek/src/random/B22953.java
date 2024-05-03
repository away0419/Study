package random;

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
        int[] chef = new int[N];
        List<int[]> list = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            chef[i] = Integer.parseInt(st.nextToken());
        }
        combination(0, 0, C, N, chef, list);
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

    public static void combination(int idx, int cnt, int C, int N, int[] chef, List<int[]> list) {

        if (cnt == C) {
            int[] copy = new int[N];
            System.arraycopy(chef, 0, copy, 0, N);
            list.add(copy);
            return;
        }

        for (int i = idx; i < N; i++) {
            if (chef[i] > 1) {
                chef[i]--;
                combination(idx, cnt + 1, C, N, chef, list);
                chef[i]++;
            } else {
                combination(idx, cnt + 1, C, N, chef, list);
            }
        }
    }

}
