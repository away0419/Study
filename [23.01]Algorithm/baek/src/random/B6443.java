package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class B6443 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < n; i++) {
            char[] chars = br.readLine().toCharArray();
            int[] charCnt = new int[26];
            int size = chars.length;
            char[] result = new char[size];

            Arrays.sort(chars);
            for (char aChar : chars) {
                charCnt[aChar - 'a']++;
            }

            permutation(charCnt, result, 0, size, sb);

        }

        System.out.println(sb);

    }

    public static void permutation(int[] charCnt, char[] result, int cnt, int size, StringBuilder sb) {
        if (cnt == size) {
            sb.append(String.valueOf(result)).append("\n");
            return;
        }

        for (int i = 0; i < 26; i++) {
            if (charCnt[i] == 0) {
                continue;
            }

            charCnt[i]--;
            result[cnt] = (char)(i+'a');
            permutation(charCnt, result, cnt + 1, size, sb);
            charCnt[i]++;

        }

    }


}
