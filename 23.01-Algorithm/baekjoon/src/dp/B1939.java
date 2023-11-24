package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class B1939 {
    public static class Node {
        int next;
        int cost;
        Node perNode;

        public Node(int next, int cost, Node perNode) {
            this.next = next;
            this.cost = cost;
            this.perNode = perNode;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[n + 1];
        int start = 1000000000;
        int end = 1;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            start = Math.min(start, c);
            end = Math.max(end, c);
            nodes[a] = new Node(b, c, nodes[a]);
            nodes[b] = new Node(a, c, nodes[b]);
        }

        st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        int answer = binarySearch(start, end, A, B, n, nodes);
        System.out.println(answer);
    }

    public static boolean bfs(int A, int B, int k, int n, Node[] nodes) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visits = new boolean[n + 1];
        q.add(A);
        visits[A] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == B) {
                return true;
            }

            Node node = nodes[cur];
            while (node != null) {
                int next = node.next;
                int cost = node.cost;
                node = node.perNode;

                if (visits[next] || cost < k) {
                    continue;
                }

                q.add(next);
                visits[next] = true;

            }

        }

        return false;
    }

    public static int binarySearch(int start, int end, int A, int B, int n, Node[] nodes) {
        int max = 1;
        while (start <= end) {

            int mid = (start + end) / 2;
            if (bfs(A, B, mid, n, nodes)) {
                max = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return max;
    }


}
