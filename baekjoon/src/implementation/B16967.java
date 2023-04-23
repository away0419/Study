package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
배열 복원하기
H, W : 행렬
X, Y : 주어지는 수
 */
public class B16967 {
    static int H, W, X, Y;
    static int[][] A, B;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());
        A = new int[H][W];
        B = new int[H + X][W + Y];

        for (int i = 0; i < H + X; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < W + Y; j++) {
                B[i][j] = Integer.parseInt(st.nextToken());
            }
        }



        for (int i = X; i < H+X; i++) {
            for (int j = Y; j < W+Y; j++) {
                B[i][j] -= B[i-X][j-Y];
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                sb.append(B[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

}
