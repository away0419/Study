package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B1707_2 {

    public static class Info {
        int no;
        boolean team;

        public Info(int no, boolean team) {
            this.no = no;
            this.team = team;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            String answer = "YES";
            List<Integer>[] edgeLists = new ArrayList[V];

            for (int i = 0; i < V; i++) {
                edgeLists[i] = new ArrayList<>();
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;

                edgeLists[a].add(b);
                edgeLists[b].add(a);
            }

            boolean[] visits = new boolean[V];
            boolean[] teams = new boolean[V];

            for (int i = 0; i < V; i++) {
                if (!visits[i] && edgeLists[i].size() > 0) {
                    if (bfs(i, visits, teams, edgeLists)) {
                        answer = "NO";
                        break;
                    }
                }
            }

            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }


    public static boolean bfs(int start, boolean[] visits, boolean[] teams, List<Integer>[] edgeLists) {
        Queue<Info> q = new ArrayDeque<>();
        q.add(new Info(start, true));
        teams[start] = true;
        visits[start] = true;


        while (!q.isEmpty()) {
            Info info = q.poll();

            for (int next :
                    edgeLists[info.no]) {
                if (visits[next] && info.team == teams[next]) {
                    return true;
                } else if (!visits[next]) {
                    visits[next] = true;
                    teams[next] = !info.team;
                    q.add(new Info(next, teams[next]));
                }
            }
        }
        return false;


    }

}
