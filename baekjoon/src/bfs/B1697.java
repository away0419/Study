package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
숨바꼭질
N : 수빈이 점
K : 동생 점

 */
public class B1697 {
    static int N, K;

    public static class Info {
        int x;
        int leng;

        public Info(int x, int leng) {
            this.x = x;
            this.leng = leng;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        int answer = bfs();
        System.out.println(answer);
    }

    public static int bfs() {
        Queue<Info> q = new ArrayDeque<>();
        q.add(new Info(N, 0));
        boolean visit[] = new boolean[100001];
        visit[N] = true;

        while (!q.isEmpty()) {
            Info info = q.poll();

            if (info.x == K) {
                return info.leng;
            }

            int arr[] = new int[3];
            arr[0] = info.x * 2;
            arr[1] = info.x - 1;
            arr[2] = info.x + 1;

            for (int i = 0; i < 3; i++) {
                if (arr[i] < 0 || arr[i] > 100000 || visit[arr[i]]) {
                    continue;
                }
                visit[arr[i]] = true;
                q.add(new Info(arr[i], info.leng + 1));
            }
        }

        return -1;
    }

}
