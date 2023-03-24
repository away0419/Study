package datastructures;

/*
도전 숫자왕
N : 주어진 수
arr : 주어진 배열
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class B23057 {
    static int N, sum;
    static Set<Integer> set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        set = new HashSet<>();
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            int k = Integer.parseInt(st.nextToken());
            Set<Integer> set2 = new HashSet<>(set);
            for (int item : set2) {
                set.add(item + k);
            }
            set.add(k);
            sum += k;
        }

        int answer = 0;
        for (int item :
                set) {
            if (item <= sum) {
                answer++;

            }
        }
        System.out.println(sum-answer);
    }

}
