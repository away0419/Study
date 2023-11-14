package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
회사에 있는 사람
 */
public class B7785 {
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Set<String> set = new HashSet<>();
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            String state = st.nextToken();
            if ("enter".equals(state)) {
                set.add(name);
            } else if ("leave".equals(state)) {
                set.remove(name);
            }
        }
        StringBuilder sb = new StringBuilder();
        set.stream().sorted(Collections.reverseOrder()).forEach(s -> sb.append(s).append("\n"));

        System.out.println(sb);

    }
}
