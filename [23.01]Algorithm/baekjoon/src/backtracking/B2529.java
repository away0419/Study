package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
부등호
K : 부등호 수
signs[] : 부등호 배열
arr[] : 정수
visit[] : 정수 방문 배열

 */
public class B2529 {
    static int K, arr[];
    static long min, max;
    static boolean visit[], signs[];
    static String minStr;

    public static void permutation(int cnt, int cur, String str) {
        if (cnt == K+1) {
            long k = Long.parseLong(str);
            if(min>k){
                min = k;
                minStr = str;
            }
            max = Math.max(max, k);
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (!visit[i]) {
                if ((signs[cnt] && cur < arr[i]) || (!signs[cnt] && cur > arr[i])) {
                    continue;
                }
                visit[i] = true;
                permutation(cnt + 1, arr[i], str + i);
                visit[i] = false;
            }
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        signs = new boolean[K];
        arr = new int[10];
        visit = new boolean[10];
        min = Long.MAX_VALUE;
        for (int i = 0; i < K; i++) {
            signs[i] = ("<".equals(st.nextToken())) ? false : true;
        }
        for (int i = 0; i < 10; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < 10; i++) {
            visit[i] = true;
            permutation(1, i, String.valueOf(i));
            visit[i] = false;

        }

        System.out.println(max);
        System.out.println(minStr);


    }

}
