package prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
N : 학생 수
K : 조는 수
Q : 보낸 수
M : 구간 수

 */
public class B20438 {
    static int N, M, Q, K, answer, sum[];
    static Set<Integer> set;
    static boolean check[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        set = new HashSet<>();
        sum = new int[N + 3];
        check = new boolean[N + 3];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            set.add(Integer.parseInt(st.nextToken()));
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            int k = Integer.parseInt(st.nextToken());
            if (!set.contains(k)) {
                int sum = k;
                while (sum < N + 3) {
                    if (!set.contains(sum)) {
                        check[sum] = true;
                    }
                    sum += k;

                }
            }
        }

        for (int i = 3; i < N + 3; i++) {
            if (!check[i]) {
                sum[i] = sum[i - 1] + 1;
            } else {
                sum[i] = sum[i - 1];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            sb.append(sum[e] - sum[s-1]).append("\n");
        }
        System.out.println(sb);

    }
}
