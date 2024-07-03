package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B22953_2 {
    public static List<int[]> combinationList;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int[] chefCookTimes = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            chefCookTimes[i] = Integer.parseInt(st.nextToken());
        }

        combinationList = new ArrayList<>();
        combination(0, 0, chefCookTimes, N, C);
        long answer = binarySearch(K);

        System.out.println(answer);

    }

    public static long binarySearch(int K) {
        long start = 1L;
        long end = 1000000L * 1000000L;

        while (start <= end) {
            long mid = (start + end) / 2;
            boolean isMakeQuota = false;

            for (int[] combinationItem :
                    combinationList) {
                long makeFoodCnt = 0L;

                for (int item :
                        combinationItem) {
                    makeFoodCnt += mid / item;
                }

                if (makeFoodCnt >= K) {
                    isMakeQuota = true;
                    break;
                }
            }

            if (isMakeQuota) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return end + 1;
    }

    public static void combination(int cnt, int cur, int[] chefCookTimes, int N, int C) {
        if (cnt == C) {
            int[] copyChefCookTimes = new int[N];
            System.arraycopy(chefCookTimes, 0, copyChefCookTimes, 0, N);
            combinationList.add(copyChefCookTimes);
            return;
        }

        for (int i = cur; i < N; i++) {
            if (chefCookTimes[i] == 1) {
                combination(cnt + 1, i, chefCookTimes, N, C);
                continue;
            }
            chefCookTimes[i]--;
            combination(cnt + 1, i, chefCookTimes, N, C);
            chefCookTimes[i]++;
        }
    }


}
