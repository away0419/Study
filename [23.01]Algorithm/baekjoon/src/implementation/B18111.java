package implementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
마인크래프트
N, M, B : 행, 렬, 최초 인벤토리 벽돌 수
time : 최소 시간
height : 높이
block : 인벤토리에 있는 벽돌 수

 */
public class B18111 {
    static int arr[][];

    static int time;
    static int height;
    static int block;
    static int N, M, B;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        time = Integer.MAX_VALUE;
        arr = new int[N][M];
        int max = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());

                max = Math.max(max, arr[i][j]);
            }
        }

        brute_force(max);

        System.out.println(time + " " + height);
    }

    static void brute_force(int max) {

        for (int i = 0; i <= max; i++) {
            int[] result = excavation(i);

            if (time > result[0]) {
                time = result[0];
                height = result[1];
            } else if (time == result[0] && height < result[1]) {
                height = result[1];
            }

        }
    }

    static int[] excavation(int height) {
        int block = B;
        int time = 0;
        int[] result = new int[2];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int value = arr[i][j];

                if (value == height) {
                    continue;
                }

                if (value > height) {
                    time += (value - height) * 2;
                    block += (value - height);
                } else {
                    time += height - value;
                    block -= (height - value);
                }
            }
        }

        if (block < 0) {
            result[0] = 999999999;
            return result;
        }

        result[0] = time;
        result[1] = height;

        return result;
    }

}
