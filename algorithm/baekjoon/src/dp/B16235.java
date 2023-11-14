package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class B16235 {
    public static class Info implements Comparable<Info>{
        int x;
        int y;
        int age;

        public Info(int x, int y, int age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }

        @Override
        public int compareTo(Info o) {
            return this.age-o.age;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] A = new int[n][n];
        int[][] map = new int[n][n];
        Queue<Info> treeQ = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
                map[i][j] = 5;
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int age = Integer.parseInt(st.nextToken());
            treeQ.add(new Info(x, y, age));
        }

        while (k-- > 0) {
            if(treeQ.isEmpty()){
                break;
            }

            Queue<Info> dieQ = spring(map, treeQ);
            summer(map, dieQ);
            fall(n, treeQ);
            winter(n, map, A);
        }

        System.out.println(treeQ.size());
    }

    public static Queue<Info> spring(int[][] map, Queue<Info> treeQ) {
        Queue<Info> dieQ = new ArrayDeque<>();
        PriorityQueue<Info> pq = new PriorityQueue<>(treeQ);
        treeQ.clear();

        while (!pq.isEmpty()) {
            Info info = pq.poll();
            int x = info.x;
            int y = info.y;
            int age = info.age;

            if (map[x][y] < age) {
                dieQ.add(info);
                continue;
            }

            map[x][y] -= age;
            info.age++;
            treeQ.add(info);
        }

        return dieQ;
    }

    public static void summer(int[][] map, Queue<Info> dieQ) {
        while (!dieQ.isEmpty()) {
            Info info = dieQ.poll();
            int x = info.x;
            int y = info.y;
            int age = info.age;

            map[x][y] += age / 2;
        }
    }

    public static void fall(int n, Queue<Info> treeQ) {
        int[] idx = {0, 0, 1, 1, 1, -1, -1, -1};
        int[] idy = {1, -1, -1, 1, 0, -1, 1, 0};
        int size = treeQ.size();

        while (size-- > 0) {
            Info info = treeQ.poll();
            int x = info.x;
            int y = info.y;
            int age = info.age;
            treeQ.offer(info);

            if (age % 5 != 0) {
                continue;
            }

            for (int i = 0; i < 8; i++) {
                int sx = x + idx[i];
                int sy = y + idy[i];

                if (sx < 0 || sy < 0 || sx >= n || sy >= n) {
                    continue;
                }

                treeQ.offer(new Info(sx, sy, 1));
            }
        }
    }

    public static void winter(int n, int[][] map, int[][] A) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] += A[i][j];
            }
        }
    }
}
