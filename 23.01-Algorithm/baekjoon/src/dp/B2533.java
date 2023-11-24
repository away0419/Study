package dp;

import java.io.*;
import java.util.StringTokenizer;

public class B2533 {
    public static class Node{
        int next;
        Node Nnode;

        Node(int next, Node Nnode){
            this.next = next;
            this.Nnode = Nnode;
        }
    }

    static boolean[] visited;
    static int result ;
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        Node[] nodes = new Node[N+1];
        visited = new boolean[N+1];
        while(N-->1){
            StringTokenizer st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            nodes[u] = new Node(v, nodes[u]);
            nodes[v] = new Node(u, nodes[v]);
        }
        int start = 1;
        result = 0;
        dfs(start, nodes);
        System.out.println(result);

    }
    private static boolean dfs(int curr, Node[] nodes) {

        visited[curr]= true;
        boolean leaf = true;
        int all = 0;
        int ear = 0;
        for(Node itr=nodes[curr]; itr!=null; itr=itr.Nnode){
            if(visited[itr.next]) continue;
            visited[itr.next] = true;
            leaf=false;
            all++;
            if(dfs(itr.next, nodes)){
                ear++;
            }

        }

        if(all!=ear && !leaf){
            result++;
            return true;
        }else{
            return false;
        }
    }
}


