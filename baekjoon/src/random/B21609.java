package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B21609 {
    public static class Info {
        int x;
        int y;
        int color;

        public Info(int x, int y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        int answer = 0;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (true) {
            int score = searchAndRemove(n, m, map);
            if (score == 0) {
                break;
            }
            gravity(n, map);
            rotation(n, map);
            gravity(n, map);
            answer += score;

        }

        System.out.println(answer);

    }

    public static void rotation(int n, int[][] map) {
        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = map[j][n - 1 - i];
            }
        }
        for (int i = 0; i < n; i++) {
            System.arraycopy(arr[i], 0, map[i], 0, n);
        }
    }

    public static void gravity(int n, int[][] map) {
        for (int i = 0; i < n; i++) {
            Stack<Info> stack = new Stack<>();

            for (int j = 0; j < n; j++) {
                if (map[j][i] >= 0) {
                    stack.push(new Info(j, i, map[j][i]));
                } else if (map[j][i] == -1) {
                    int k = j - 1;
                    while (!stack.isEmpty()) {
                        Info info = stack.pop();
                        map[info.x][info.y] = -2;
                        map[k--][info.y] = info.color;
                    }
                }
            }

            int k = n - 1;
            while (!stack.isEmpty()) {
                Info info = stack.pop();
                map[info.x][info.y] = -2;
                map[k--][info.y] = info.color;
            }
        }
    }

    public static int searchAndRemove(int n, int m, int[][] map) {
        int score = 0;
        Queue<Info> group = new PriorityQueue<>();
        int[] rainbowCnt = new int[2];
        int[][] max = new int[2][2];
        boolean[][] mapVisit = new boolean[n][n];

        max[0][0] = -1;
        max[0][1] = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!mapVisit[i][j] && map[i][j] > 0) {
                    mapVisit[i][j] = true;
                    rainbowCnt[1] = 0;
                    max[1][0] = n;
                    max[1][1] = n;

                    Queue<Info> pq = bfs(n, map, mapVisit, new Info(i, j, map[i][j]), rainbowCnt, max);

                    if (pq.size() > group.size()) {
                        group = pq;
                        rainbowCnt[0] = rainbowCnt[1];
                        max[0][0] = max[1][0];
                        max[0][1] = max[1][1];
                    } else if (!pq.isEmpty() && pq.size() == group.size()) {
                        if (rainbowCnt[1] > rainbowCnt[0]) {
                            group = pq;
                            rainbowCnt[0] = rainbowCnt[1];
                            max[0][0] = max[1][0];
                            max[0][1] = max[1][1];

                        } else if (rainbowCnt[0] == rainbowCnt[1]) {
                            if (max[1][0] > max[0][0]) {
                                group = pq;
                                max[0][0] = max[1][0];
                                max[0][1] = max[1][1];
                            } else if (max[0][0] == max[1][0] && max[1][1] > max[0][1]) {
                                group = pq;
                                max[0][0] = max[1][0];
                                max[0][1] = max[1][1];
                            }
                        }
                    }
                }
            }
        }

        if (!group.isEmpty()) {
            score = (int) Math.pow(group.size(), 2);
            while (!group.isEmpty()) {
                Info info = group.poll();
                map[info.x][info.y] = -2;
            }
        }

        return score;
    }

    public static Queue<Info> bfs(int n, int[][] map, boolean[][] mapVisit, Info info, int[] rainbowCnt, int[][] max) {
        Queue<Info> pq = new ArrayDeque<>();
        Queue<Info> q = new ArrayDeque<>();
        boolean visit[][] = new boolean[n][n];
        int[] idx = {1, -1, 0, 0};
        int[] idy = {0, 0, 1, -1};
        int color = info.color;
        int colorSize = 1;

        q.add(info);
        visit[info.x][info.y] = true;
        max[1][0] = info.x;
        max[1][1] = info.y;

        while (!q.isEmpty()) {
            info = q.poll();
            pq.add(info);

            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + info.x;
                int sy = idy[i] + info.y;

                if (sx < 0 || sy < 0 || sx >= n || sy >= n || visit[sx][sy]) {
                    continue;
                }

                if (map[sx][sy] == 0 || map[sx][sy] == color) {
                    q.add(new Info(sx, sy, map[sx][sy]));
                    visit[sx][sy] = true;
                    if (map[sx][sy] == color) {
                        mapVisit[sx][sy] = true;
                        colorSize++;
                        max[1][0] = Math.min(max[1][0], sx);
                        max[1][1] = Math.min(max[1][1], sy);
                    }
                }
            }
        }

        if (pq.size() < 2) {
            pq.clear();
        } else {
            rainbowCnt[1] = pq.size() - colorSize;
        }

        return pq;
    }
}
