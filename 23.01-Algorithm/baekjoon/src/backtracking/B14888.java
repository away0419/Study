package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
연산자 끼워넣기
N : 갯수
arr[] : 주어진 수
operation[] : 주어진 사칙연산
 */
public class B14888 {
    static int N, arr[], signs[], min, max;
    static boolean visit[];

    public static int operation(int a, int b, int sign) {
        int res = 0;
        switch (sign) {
            case 0:
                res = a + b;
                break;
            case 1:
                res = a - b;
                break;
            case 2:
                res = a * b;
                break;
            case 3:
                res = a / b;
                break;
            default:
                break;
        }
        return res;
    }

    public static void permutation(int cnt, int res) {
        if (cnt == N - 1) {
            min = Math.min(min, res);
            max = Math.max(max, res);
            return;
        }

        for (int i = 0; i < N - 1; i++) {
            if (!visit[i]) {
                visit[i] = true;
                int r = operation(res, arr[cnt + 1], signs[i]);
                permutation(cnt + 1, r);
                visit[i] = false;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        signs = new int[N - 1];
        visit = new boolean[N - 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            int k = Integer.parseInt(st.nextToken());
            for (int j = 0; j < k; j++) {
                signs[cnt + j] = i;
            }
            cnt += k;
        }

        permutation(0, arr[0]);
        System.out.println(max);
        System.out.println(min);
    }


}

