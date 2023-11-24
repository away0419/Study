package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
T : 테스트 케이스
N : 도시 수
M : 도로 수
parent[] : 최상위 부모 배열
cost[][] : 비용 이차원 배열
answer : 정답
 */
public class B10723 {
    static int T, N, M, parents[], costs[];
    static long answer, sum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            parents = new int[N];
            costs = new int[N];
            answer = 0;
            sum = 0;
            for (int i = 0; i < N; i++) {
                parents[i] = i;
            }

            for (int i = 1; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                make(i, b, c);
            }
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                add(a, b, c);
            }

            sb.append(answer).append("\n");
        }

        System.out.println(sb);
    }


    public static void make(int a, int b, int c) {
        int temp = a;
        a = Math.max(a, b);
        if (a == b) {
            b = temp;
        }
        parents[b] = a;
        costs[b] = c;
        sum += c;
    }

    public static void add(int a, int b, int c) {
        int cc = (costs[a] > costs[b]) ? a : b;
        if (costs[cc] > c) {
            sum = sum - costs[cc] + c;
            costs[cc] = c;
            parents[cc] = b;
        }
        answer ^= sum;
    }
}
