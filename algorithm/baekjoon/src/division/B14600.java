package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
샤워실 바닥 깔기
 */
public class B14600 {
    static int map[][], num, N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine()) * 2;
        map = new int[N][N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        map [y-1][x-1] = -1;
        calc(0, 0, N);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(map[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public static void calc(int x, int y, int size) {
        num++;
        int ns = size / 2;
        if (check(x, y, ns)) map[x + ns - 1][y + ns - 1] = num;
        if (check(x + ns, y, ns)) map[x + ns][y + ns - 1] = num;
        if (check(x, y + ns, ns)) map[x + ns - 1][y + ns] = num;
        if (check(x + ns, y + ns, ns)) map[x + ns][y + ns] = num;

        if (size == 2) return;

        calc(x, y, ns);
        calc(x + ns, y, ns);
        calc(x, y + ns, ns);
        calc(x + ns, y + ns, ns);

    }

    public static boolean check(int x, int y, int s) {
        for (int i = x; i < x + s; i++) {
            for (int j = y; j < y + s; j++) {
                if (map[i][j] != 0) return false;
            }
        }

        return true;
    }

}
