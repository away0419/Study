package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

public class B1005 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()) + 1;
            int K = Integer.parseInt(st.nextToken());
            int[] time = new int[N];
            int[] dp = new int[N];
            int[] level = new int[N];
            List<Integer>[] lists = new List[N];

            for (int i = 0; i < N; i++) {
                lists[i] = new ArrayList<>();
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < N; i++) {
                time[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());

                lists[s].add(e);
                level[e]++;
            }

            int W = Integer.parseInt(br.readLine());
            long answer = fn(W, N, lists, time, dp, level);
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }

    public static long fn(int W, int N, List<Integer>[] lists, int[] time, int[] dp, int[] level) {
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 1; i < N; i++) {
            if (level[i] == 0) {
                q.add(i);
            }
            dp[i] = time[i];
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            if (cur == W) {
                break;
            }

            for (int next : lists[cur]) {
                if (--level[next] == 0) {
                    q.add(next);
                }
                dp[next] = Math.max(dp[next], dp[cur] + time[next]);
            }


        }

        return dp[W];
    }

//    public static long fn(int w, List<Integer>[] lists, int[] time, int[] dp) {
//        if (dp[w] != 0) {
//            return dp[w];
//        }
//
//        dp[w] = time[w];
//        long max = 0L;
//        for (int next : lists[w]) {
//            max = Math.max(fn(next, lists, time, dp), max);
//        }
//
//        dp[w] += max;
//        return dp[w];
//    }
}
