package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
킹
king : 킹 위치
stone : 돌 위치
N : 움직이는 횟수

 */
public class B1063 {

    public static class Info {
        int[] stone;
        int[] king;

        public Info(int[] stone, int[] king) {
            this.stone = stone;
            this.king = king;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String king = st.nextToken();
        String stone = st.nextToken();
        int N = Integer.parseInt(st.nextToken());
        int[] res = new int[2];
        Info info = new Info(position(stone), position(king));

        for (int i = 0; i < N; i++) {
            int vector = vector(br.readLine());
            move(info, vector);
        }

        String k = (char) ('A' + info.king[1] - 1) + String.valueOf(info.king[0]);
        String s = (char) ('A' + info.stone[1] - 1) + String.valueOf(info.stone[0]);

        System.out.println(k);
        System.out.println(s);
    }

    public static void move(Info info, int vector) {
        int[] idx = {1, 1, 1, -1, -1, -1, 0, 0};
        int[] idy = {0, 1, -1, 0, 1, -1, -1, 1};
        int[] mking = {info.king[0] + idx[vector], info.king[1] + idy[vector]};
        int[] mstone = {info.stone[0] + idx[vector], info.stone[1] + idy[vector]};

        if (!check(mking[0], mking[1])) {
            return;
        }

        if (mking[0] == info.stone[0] && mking[1] == info.stone[1]) {
            if (!check(mking[0], mking[1]) || !check(mstone[0], mstone[1]))
                return;
            info.king[0] += idx[vector];
            info.king[1] += idy[vector];
            info.stone[0] += idx[vector];
            info.stone[1] += idy[vector];
        } else {
            info.king[0] += idx[vector];
            info.king[1] += idy[vector];
        }

    }

    public static boolean check(int x, int y) {
        return x >= 1 && y >= 1 && x <= 8 && y <= 8;
    }


    public static int vector(String str) {
        str = str.toUpperCase();
        int res = -1;
        switch (str) {
            case "T":
                res = 0;
                break;
            case "RT":
                res = 1;
                break;
            case "LT":
                res = 2;
                break;
            case "B":
                res = 3;
                break;
            case "RB":
                res = 4;
                break;
            case "LB":
                res = 5;
                break;
            case "L":
                res = 6;
                break;
            case "R":
                res = 7;
                break;
        }

        return res;

    }

    public static int[] position(String str) {
        int[] res = new int[2];

        if (str.charAt(0) >= '1' && str.charAt(0) <= '8') {
            res[0] = str.charAt(0) - '0';
            res[1] = Character.toUpperCase(str.charAt(1)) - 'A' + 1;
        } else {
            res[0] = str.charAt(1) - '0';
            res[1] = Character.toUpperCase(str.charAt(0)) - 'A' + 1;
        }

        return res;
    }
}
