package prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
소수 부분 수열
T : 테스트 케이스
arr[] : 주어진 배열
sum[] : 누적합
prime[] : 소수
 */
public class B6884 {
    static int T, arr[], sum[];
    static boolean prime[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        prime = new boolean[100000001];
        primeMake();
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());
            sum = new int[cnt + 1];
            arr = new int[cnt + 1];
            for (int i = 1; i < sum.length; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                sum[i] = sum[i - 1] + arr[i];
            }

            boolean check = false;
            loop:
            for (int i = 2; i <= cnt; i++) {
                for (int j = i; j <= cnt; j++) {
                    int k = sum[j] - sum[j - i];

                        if (!prime[k]) {
                            sb.append("Shortest primed subsequence is length ").append(i).append(": ");
                            for (int l = j - i + 1; l <= j; l++) {
                                sb.append(arr[l]).append(" ");
                            }
                            check = true;
                            break loop;
                        }
                }
            }
            if (!check) {
                sb.append("This sequence is anti-primed.");
            }
            sb.append("\n");
        }
        System.out.println(sb);


    }

    public static void primeMake() {
        prime[0] = prime[1] = true;
        for (int i = 2; i * i <= 100000000; i++) {
            if (!prime[i]) {
                for (int j = i * i; j <= 100000000; j += i) {
                    prime[j] = true;
                }
            }
        }
    }

}
