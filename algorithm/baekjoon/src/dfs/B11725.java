package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
트리의 부모 찾기
N : 노드 갯수

 */
public class B11725 {
    static int N, arr[];
    static List<Integer>[] list;
    static boolean visit[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            list[i] = new ArrayList<>();
        }
        arr = new int[N + 1];
        visit = new boolean[N + 1];

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list[a].add(b);
            list[b].add(a);
        }
        dfs(1);

        for (int i = 2; i < N + 1; i++) {
            System.out.println(arr[i]);
        }

    }

    public static void dfs(int a) {
        visit[a] = true;
        for (int next :
                list[a]) {
            if (!visit[next]) {
                visit[next] = true;
                arr[next] = a;
                dfs(next);
            }
        }
    }

}
