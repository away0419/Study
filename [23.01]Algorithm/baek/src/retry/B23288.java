package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B23288 {

    public static class Dice {
        int top;
        int north;
        int east;
        int west;
        int south;
        int bottom;


        public Dice(int top, int north, int east, int west, int south, int bottom) {
            this.top = top;
            this.north = north;
            this.east = east;
            this.west = west;
            this.south = south;
            this.bottom = bottom;
        }

        public void move(int vec) {
            switch (vec) {
                case 0 -> { // 동
                    int temp = this.bottom;
                    this.bottom = this.east;
                    this.east = this.top;
                    this.top = this.west;
                    this.west = temp;

                }
                case 1 -> { // 서
                    int temp = this.bottom;
                    this.bottom = this.west;
                    this.west = this.top;
                    this.top = this.east;
                    this.east = temp;
                }
                case 2 -> { // 남
                    int temp = this.bottom;
                    this.bottom = this.south;
                    this.south = this.top;
                    this.top = this.north;
                    this.north = temp;
                }
                case 3 -> { // 북
                    int temp = this.bottom;
                    this.bottom = this.north;
                    this.north = this.top;
                    this.top = this.south;
                    this.south = temp;

                }
                default -> {
                    System.out.println("잘못된 입력입니다.");
                }
            }
        }
    }

    public static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        Dice dice = new Dice(1, 2, 3, 4, 5, 6);
        int[][] arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(move(N, M, K, arr, dice));
    }

    public static int move(int N, int M, int K, int[][] arr, Dice dice) {
        int[] idx = {0, 0, 1, -1};
        int[] idy = {1, -1, 0, 0};
        int vec = 0;
        Position p = new Position(0, 0);
        int[][] dp = new int[N][M];
        int score = 0;

        while (K-- > 0) {
            int sx = p.x + idx[vec];
            int sy = p.y + idy[vec];

            if (sx < 0 || sy < 0 || sx >= N || sy >= M) {
                switch (vec) {
                    case 0 -> vec = 1;
                    case 1 -> vec = 0;
                    case 2 -> vec = 3;
                    case 3 -> vec = 2;
                    default -> System.out.println("잘못된 입력");
                }
            }

            p.x += idx[vec];
            p.y += idy[vec];
            dice.move(vec);

            int value = arr[p.x][p.y];
            int cnt = bfs(N, M, arr, dp, p);

            score += cnt * value;

            if(dice.bottom > value){
                switch (vec){
                    case 0 -> vec = 2; // 동 -> 남
                    case 1 -> vec = 3; // 서 -> 북
                    case 2 -> vec = 1; // 남 -> 서
                    case 3 -> vec = 0; // 북 -> 동
                    default -> System.out.println("잘못된 입력입니다.");
                }
            }else if(dice.bottom < value) {
                switch (vec) {
                    case 0 -> vec = 3; // 동 -> 북
                    case 1 -> vec = 2; // 서 -> 남
                    case 2 -> vec = 0; // 남 -> 동
                    case 3 -> vec = 1; // 북 -> 서
                    default -> System.out.println("잘못된 입력입니다.");
                }
            }
        }

        return score;
    }

    public static int bfs(int N, int M, int[][] arr, int[][] dp, Position p) {
        if (dp[p.x][p.y] != 0) {
            return dp[p.x][p.y];
        }

        int[] idx = {0, 0, 1, -1};
        int[] idy = {1, -1, 0, 0};
        Queue<Position> q = new ArrayDeque<>();
        Queue<Position> q2 = new ArrayDeque<>();
        int cnt = 1;

        q.add(p);
        dp[p.x][p.y] = cnt;

        while (!q.isEmpty()) {
            Position cur = q.poll();
            q2.add(cur);
            for (int i = 0; i < 4; i++) {
                int sx = cur.x + idx[i];
                int sy = cur.y + idy[i];

                if (sx < 0 || sy < 0 || sx >= N || sy >= M || dp[sx][sy] != 0 || arr[cur.x][cur.y] != arr[sx][sy]) {
                    continue;
                }

                q.add(new Position(sx, sy));
                dp[sx][sy] = cnt++;
            }
        }

        while (!q2.isEmpty()) {
            Position cur = q2.poll();
            dp[cur.x][cur.y] = cnt;
        }

        return cnt;
    }

}
