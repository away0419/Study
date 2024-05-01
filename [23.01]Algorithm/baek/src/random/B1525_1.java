package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B1525_1 {
    public static class Info {
        int x;
        int y;
        int leng;
        int[][] arr;

        public Info(int x, int y, int leng, int[][] arr) {
            this.x = x;
            this.y = y;
            this.leng = leng;
            this.arr = arr;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] arr = new int[3][3];
        int x = 0;
        int y = 0;
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if (arr[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        int answer = bfs(arr, x, y);
        System.out.println(answer);
    }

    public static int bfs(int[][] arr, int x, int y) {
        Queue<Info> q = new ArrayDeque<>();
        Set<Integer> set = new HashSet<>();
        int[] idx = {0, 0, -1, 1};
        int[] idy = {-1, 1, 0, 0};
        int init = arrToString(arr);

        q.add(new Info(x, y, 0, arr));
        set.add(init);

        while (!q.isEmpty()) {
            Info cur = q.poll();
            int curString = arrToString(cur.arr);

            if (curString == 87654321) {
                return cur.leng;
            }


            for (int i = 0; i < 4; i++) {
                int sx = cur.x + idx[i];
                int sy = cur.y + idy[i];

                if (sx >= 3 || sx < 0 || sy >= 3 || sy < 0) {
                    continue;
                }

                int[][] next = new int[3][3];
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        next[j][k] = cur.arr[j][k];
                    }
                }

                int temp = next[cur.x][cur.y];
                next[cur.x][cur.y] = next[sx][sy];
                next[sx][sy] = temp;

                int nextString = arrToString(next);
                if (set.add(nextString)) {
                    q.add(new Info(sx, sy, cur.leng + 1, next));
                }
            }

        }

        return -1;
    }


    public static int arrToString(int[][] arr) {
        int res = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int k = (i * 3) + j;
                res += arr[i][j] * (int) Math.pow(10, k);
            }
        }

        return res;
    }

}
