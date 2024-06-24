package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B9663_2 {

    public static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] queenCoordinate = new int[N];

        fn(0, 0, N, queenCoordinate);

        System.out.println(answer);

    }

    public static void fn(int cnt, int x, int N, int[] queenCoordinate) {
        if (cnt == N) {
            answer++;
            return;
        }

        for (int i = 0; i < N; i++) {
            queenCoordinate[x] = i;
            if (isQueenAble(x, i, N, queenCoordinate)) {
                fn(cnt + 1, x + 1, N, queenCoordinate);
            }
        }

    }

    public static boolean isQueenAble(int x, int y, int N, int[] queenCoordinate) {

        for (int i = 0; i < x; i++) {
            if (queenCoordinate[i] == queenCoordinate[x]) {
                return false;
            } else if (Math.abs(queenCoordinate[x] - queenCoordinate[i]) == Math.abs(x - i)) {
                return false;
            }
        }

        return true;
    }


}
