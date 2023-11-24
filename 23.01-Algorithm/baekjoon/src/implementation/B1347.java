package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/*
미로 만들기
 */
public class B1347 {
    static int[] idx = {1, 0, -1, 0};
    static int[] idy = {0, -1, 0, 1};
    static int minx, miny, maxx, maxy;
    static Queue<int[]> q;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String str = br.readLine();
        q = new ArrayDeque<>();

        int vec = 0;
        int x = 0;
        int y = 0;

        q.add(new int[]{0, 0});
        for (int i = 0; i < N; i++) {
            char command = str.charAt(i);
            int res[] = take(command, vec, x, y);
            vec = res[0];
            x = res[1];
            y = res[2];
        }

        boolean map[][] = new boolean[maxx - minx + 1][maxy - miny + 1];

        while (!q.isEmpty()) {
            int[] p = q.poll();
            map[p[0] - minx][p[1] - miny] = true;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]) {
                    sb.append('.');
                } else {
                    sb.append('#');
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);


    }

    public static int[] take(char command, int vec, int x, int y) {
        switch (command) {
            case 'R':
                vec = (vec + 1) % 4;
                break;
            case 'L':
                vec = (vec + 3) % 4;
                break;
            case 'F':
                x += idx[vec];
                y += idy[vec];
                minx = Math.min(minx, x);
                miny = Math.min(miny, y);
                maxx = Math.max(maxx, x);
                maxy = Math.max(maxy, y);

                q.add(new int[]{x, y});
                break;
        }

        return new int[]{vec, x, y};
    }
}
