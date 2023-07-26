package backtracking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class B16571 {
    static char answer;

    public static class Info {
        int x;
        int y;
        int state;

        public Info(int x, int y, int state) {
            this.x = x;
            this.y = y;
            this.state = state;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] map = new int[3][3];
        int p1 = 0;
        int p2 = 0;
        int next = 2;
        answer = 'D';
        List<Info> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) {
                    p1++;
                } else if (map[i][j] == 2) {
                    p2++;
                } else {
                    list.add(new Info(i, j, 0));
                }
            }
        }

        if (p2 == p1) {
            next = 1;
        }
        boolean[] visits = new boolean[list.size()];
        int start = next;
        for (int i = 0; i < list.size(); i++) {
            Info info = list.get(i);
            visits[i] = true;
            map[info.x][info.y] = next;
            fn(info.x, info.y, next == 1 ? 2 : 1, 0, start, list, visits, map);
            map[info.x][info.y] = 0;
        }

        System.out.println(answer);

    }


    public static void fn(int x, int y, int player, int cnt, int start, List<Info> list, boolean[] visits, int[][] map) {
        if (check(x, y, map)) {
            if (map[x][y] == start) {
                System.out.println("W");
                System.exit(0);
            } else {
                answer = 'L';
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (visits[i]) {
                continue;
            }
            Info info = list.get(i);
            visits[i] = true;
            map[info.x][info.y] = player;
            fn(info.x, info.y, player == 1 ? 2 : 1, cnt + 1, start, list, visits, map);
            map[info.x][info.y] = 0;
            visits[i] = false;
        }

    }

    public static boolean check(int x, int y, int[][] map) {
        int[][] movex = {{0, 0}, {1, -1}, {1, -1}, {1, -1}};
        int[][] movey = {{-1, 1}, {0, 0}, {1, -1}, {-1, 1}};

        for (int i = 0; i < 4; i++) {
            Stack<Info> stack = new Stack<>();
            boolean[][] visits = new boolean[3][3];
            int cnt = 1;

            stack.push(new Info(x, y, map[x][y]));
            visits[x][y] = true;

            while (!stack.isEmpty()) {
                Info cur = stack.pop();

                for (int j = 0; j < 2; j++) {
                    int sx = cur.x + movex[i][j];
                    int sy = cur.y + movey[i][j];

                    if (sx < 0 || sy < 0 || sx >= 3 || sy >= 3 || visits[sx][sy] || map[sx][sy] != cur.state) {
                        continue;
                    }

                    cnt++;
                    visits[sx][sy] = true;
                    stack.push(new Info(sx, sy, map[sx][sy]));

                }
            }

            if (cnt == 3) {
                return true;
            }
        }
        return false;
    }

}
