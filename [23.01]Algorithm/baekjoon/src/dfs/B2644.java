package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
촌수계산
N : 사람 수
M : 관계 수
A, B : 구해야하는 사람 번호
 */
public class B2644 {
    public static int N, M, A, B;
    public static List<Integer>[] list;

    public static class Info {
        int no;
        int leng;

        public Info(int no, int leng) {
            this.no = no;
            this.leng = leng;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken()) - 1;
        B = Integer.parseInt(st.nextToken()) - 1;
        M = Integer.parseInt(br.readLine());
        list = new List[N];
        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            list[x].add(y);
            list[y].add(x);
        }

        Queue<Info> q = new ArrayDeque<>();
        q.add(new Info(A, 0));
        boolean visit[] = new boolean[N];
        visit[A] = true;
        while (!q.isEmpty()) {
            Info info = q.poll();
            int cur = info.no;

            if (cur == B) {
                System.out.println(info.leng);
                return;
            }
            for (int next :
                    list[cur]) {
                if (visit[next]) {
                    continue;
                }
                visit[next] = true;
                q.add(new Info(next, info.leng + 1));
            }
        }

        System.out.println(-1);
    }


}
