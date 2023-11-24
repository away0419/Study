package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class B1516 {
    public static void main(String[] args) throws IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] time = new int[N + 1];
        int[] level = new int[N + 1];
        long[] dp = new long[N + 1];
        List<Integer>[] lists = new List[N + 1];

        for (int i = 0; i <= N; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            time[i] = Integer.parseInt(st.nextToken());

            int next = Integer.parseInt(st.nextToken());
            while (next != -1) {
                level[i]++;
                lists[next].add(i);
                next = Integer.parseInt(st.nextToken());
            }
        }
        fn(N, lists, time, level, dp);
        StringBuilder sb = new StringBuilder();

        for (int i=1; i<=N; i++) {
            sb.append(dp[i]).append("\n");
        }

        System.out.println(sb);

    }

    public static void fn(int N, List<Integer>[] lists, int[] time, int[] level, long[] dp) {
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 1; i <= N; i++) {
            dp[i] = time[i];
            if (level[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : lists[cur]) {
                if (--level[next] == 0) {
                    q.add(next);
                }

                dp[next] = Math.max(dp[next], dp[cur] + time[next]);
            }
        }
    }

}
