package datastructures;

/*
유전자 조합

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class B25758 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        Set<Integer> set = new HashSet<>();
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i = 0; i < N; i++) {
            String str = st.nextToken();
            int a = str.charAt(0);
            int b = str.charAt(1);

            for (Integer integer : set) {
                if (integer < b) {
                    set2.add(b);
                } else {
                    set2.add(integer);
                }
            }
            for (Integer integer : set1) {
                if (integer < a) {
                    set2.add(a);
                } else {
                    set2.add(integer);
                }
            }
            set.add(a);
            set1.add(b);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(set2.size()).append("\n");
        set2.stream().sorted().forEach(e -> sb.append((char) e.intValue()).append(" "));
        System.out.println(sb);

    }
}
