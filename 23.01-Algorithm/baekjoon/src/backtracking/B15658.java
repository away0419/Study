package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
연산자 끼워넣기 (2)
N : 주어진 수
arr[] : 주어진 배열
operations[] : +0, -1, x2, /3
 */
public class B15658 {
    static int N, arr[], MIN, MAX;
    static int operations[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        MIN = Integer.MAX_VALUE;
        MAX = Integer.MIN_VALUE;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        operations = new int[4];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            operations[i] = Integer.parseInt(st.nextToken());
        }
        fn(1, arr[0]);
        System.out.println(MAX + "\n" + MIN);
    }

    public static void fn(int cnt, int sum) {
        if (cnt == N) {
            MAX = Math.max(MAX, sum);
            MIN = Math.min(MIN, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operations[i] == 0) continue;
            int res = calculation(i, arr[cnt], sum);
            operations[i]--;
            fn(cnt + 1, res);
            operations[i]++;
        }
    }


    public static int calculation(int operation, int x, int sum) {
        int res = sum;
        switch (operation) {
            case 0:
                res += x;
                break;
            case 1:
                res -= x;
                break;
            case 2:
                res *= x;
                break;
            case 3:
                res /= x;
                break;
            default:
                break;
        }
        return res;
    }
}
