package level3_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1005 {
    public static class Node {
        int no;
        Node linkNode;

        public Node(int no, Node linkNode) {
            this.no = no;
            this.linkNode = linkNode;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[] times = new int[N];
            long[] dp = new long[N];
            int[] levels = new int[N];
            boolean[] visits = new boolean[N];
            Node[] nodes = new Node[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                times[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int prev = Integer.parseInt(st.nextToken()) - 1;
                int next = Integer.parseInt(st.nextToken()) - 1;

                nodes[next] = new Node(prev, nodes[next]);
                levels[next]++;
            }

            int target = Integer.parseInt(br.readLine())-1;
            long answer = fn(target, N, K, times, dp, visits, nodes);
            sb.append(answer).append("\n");
        }

        System.out.println(sb);

    }

    public static long fn(int target, int N, int K, int[] times, long[] dp, boolean[] visits, Node[] nodes) {

        if (visits[target]) {
            return dp[target];
        }
        visits[target] = true;
        dp[target] = times[target];

        for (Node node = nodes[target]; node != null; node = node.linkNode) {
            dp[target] = Math.max(dp[target], fn(node.no, N, K, times, dp, visits, nodes)+times[target]);
        }

        return dp[target];

    }

}
