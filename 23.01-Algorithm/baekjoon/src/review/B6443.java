package review;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class B6443 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            char[] chars = str.toCharArray();
            int[] cnts = new int[26];
            int length = chars.length;

            for (int j = 0; j < length; j++) {
                int idx = chars[j] - 'a';
                cnts[idx]++;
            }

            permutation(0, new char[length], answer, cnts, length);
        }

        System.out.println(answer);
    }

    public static void permutation(int cnt, char[] word, StringBuilder answer, int[] cnts, int length) {
        if (cnt == length) {
            answer.append(String.valueOf(word)).append("\n");
            return;
        }

        for (int i = 0; i < 26; i++) {
            if (cnts[i] > 0) {
                char ch = (char) ('a' + i);
                word[cnt] = ch;
                cnts[i]--;
                permutation(cnt + 1, word, answer, cnts, length);
                cnts[i]++;
            }
        }


    }


}
