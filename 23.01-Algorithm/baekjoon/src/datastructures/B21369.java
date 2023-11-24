package datastructures;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
이진수 더하기
T : 테스트케이스
n : 정수 갯수
x : 만들어야하는 수
arr[] : 주어지는 배열
 */
public class B21369 {
    static int T, N, X;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            Map<Long, Long> map = new HashMap<>();
            long answer = 0;

            for (int i = 0; i < N; i++) {
                long key = Long.parseLong(st.nextToken());
                long xor = key ^ X;
                if (map.containsKey(xor)) {
                    answer += map.get(xor);
                }
                map.put(key, map.getOrDefault(key, 0L) + 1);
            }

            sb.append(answer).append("\n");

        }
        System.out.println(sb);
    }


}
