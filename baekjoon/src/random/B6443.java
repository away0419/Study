package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class B6443 {
    static StringBuilder answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        answer = new StringBuilder();
        Set<String> set = new HashSet<>();

        while (n-- > 0) {
            String str = br.readLine().chars()
                    .mapToObj(c -> (char) c)
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining());
            boolean[] visit = new boolean[str.length()];
            char[] chars = new char[str.length()];
            permutation(0, visit, str, chars, set);
            set.clear();
        }

        System.out.println(answer);
    }

    public static void permutation(int cnt, boolean[] visit, String str, char[] chars, Set<String> set) {
        if (cnt == str.length()) {
            String s = String.valueOf(chars);
            if (set.add(s)) {
                answer.append(s).append("\n");
            }
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!visit[i]) {
                visit[i] = true;
                chars[cnt] = str.charAt(i);
                permutation(cnt + 1, visit, str, chars, set);
                visit[i] = false;
            }
        }

    }
}
