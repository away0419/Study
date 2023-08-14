package level3_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2533 {
    static long answer;
    public static class Node{
        int no;
        Node linkNode;

        public Node(int no, Node linkNode) {
            this.no = no;
            this.linkNode = linkNode;
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        Node[] nodes = new Node[N+1];
        boolean[] visits = new boolean[N+1];

        for(int i=0; i<N-1; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            nodes[a] = new Node(b, nodes[a]);
            nodes[b] = new Node(a, nodes[b]);
        }


        dfs(1, nodes, visits);

        System.out.println(answer);
    }

    public static boolean dfs(int idx, Node[] nodes, boolean[] visits){
        visits[idx] = true;
        boolean earlyCheck = false;

        for (Node linkNode = nodes[idx]; linkNode != null ; linkNode = linkNode.linkNode) {
            if (visits[linkNode.no]) {
                continue;
            }

            if (!dfs(linkNode.no, nodes, visits)) {
                earlyCheck = true;
            }
        }

        if(earlyCheck){
            answer++;
        }

        return earlyCheck;
    }
}
