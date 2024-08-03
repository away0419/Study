package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B1707 {
    public static class Info {
        int no;
        int level;

        public Info(int no, int level) {
            this.no = no;
            this.level = level;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (K-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            List<Integer>[] edgeList = new List[V];

            for (int i = 0; i < V; i++) {
                edgeList[i] = new ArrayList<>();
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;

                edgeList[a].add(b);
                edgeList[b].add(a);

            }

            sb.append(bfs(V, edgeList)).append("\n");
        }

        System.out.println(sb);
    }


    public static String bfs(int V, List<Integer>[] edgeList) {
        Queue<Info> q = new ArrayDeque<>();
        boolean[] visits = new boolean[V];
        int[] levels = new int[V];
        for (int i = 0; i < V; i++) {
            if (edgeList[i].size() > 0) {
                q.add(new Info(i, 1));
                levels[i] = 1;
            }
        }


        while (!q.isEmpty()) {
            Info info = q.poll();
            visits[info.no] = true;

            for (int next :
                    edgeList[info.no]) {

                if (visits[next]) {
                    if (levels[next] == levels[info.no]) {
                        return "NO";
                    }
                } else {
                    levels[next] = (info.level + 1) % 2;
                    q.add(new Info(next, levels[next]));
                }
            }
        }

        return "YES";

    }

}
