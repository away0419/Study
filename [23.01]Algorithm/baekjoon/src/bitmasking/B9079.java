package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
동전 게임
state : 방문 상태
visit[] : 방문 체그 배열

 */
public class B9079 {
    public static class Info {
        int state;
        int leng;

        public Info(int state, int leng) {
            this.state = state;
            this.leng = leng;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (tc-- > 0) {
            int state = 0;
            for (int i = 0; i < 3; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 3; j++) {
                    if (st.nextToken().charAt(0) == 'H') {
                        state |= 1 << (i * 3 + j);
                    }
                }
            }

            int res = bfs(state);
            sb.append(res).append("\n");
        }
        System.out.println(sb);
    }

    public static int bfs(int state) {
        Queue<Info> q = new ArrayDeque<>();
        boolean visit[] = new boolean[1 << 10];
        q.add(new Info(state, 0));
        visit[state] = true;

        while (!q.isEmpty()) {
            Info info = q.poll();
            if (info.state == 0b111111111 || info.state==0) {
                return info.leng;
            }
            for (int i = 0; i < 8; i++) {
                int res = turn(info.state, i);
                if (visit[res])
                    continue;
                visit[res] = true;
                q.add(new Info(res, info.leng + 1));
            }
        }

        return -1;
    }

    public static int turn(int state, int type) {
        int bit = 0;
        switch (type) {
            case 0:
                bit = 0b111000000;
                break;
            case 1:
                bit = 0b000111000;
                break;
            case 2:
                bit = 0b000000111;
                break;
            case 3:
                bit = 0b001001001;
                break;
            case 4:
                bit = 0b100100100;
                break;
            case 5:
                bit = 0b010010010;
                break;
            case 6:
                bit = 0b100010001;
                break;
            case 7:
                bit = 0b001010100;
                break;
            default:
                break;
        }
        return state ^ bit;
    }
}
