package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
비슷한 단어

 */
public class B2607 {
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Map<Character, Integer> map = new HashMap<>();
        String str = br.readLine();
        int answer = 0;
        for (int i = 0; i < str.length(); i++) {
            map.put(str.charAt(i), map.getOrDefault(str.charAt(i), 0) + 1);
        }


        for (int i = 1; i < N; i++) {
            Map<Character, Integer> temap = new HashMap<>();
            char[] charr = br.readLine().toCharArray();

            for (char c : charr) {
                temap.put(c, temap.getOrDefault(c, 0) + 1);
            }

            int sum = charr.length;
            int cnt = 0;
            boolean c = true;
            for (char ch :
                    map.keySet()) {
                int a = map.get(ch);
                int b = temap.getOrDefault(ch, 0);
                if (a != b) {
                    if (Math.abs(a - b) > 2) {
                        c = false;
                        break;
                    } else if (Math.abs(a - b) == 1) {
                        if (cnt>1) {
                            c = false;
                            break;
                        } else {
                            cnt++;
                        }
                    }
                }
                sum -= b;
            }

            if (sum > 1 || Math.abs(str.length()-charr.length)>1) {
                c = false;
            }
            if (c) {
                answer++;
            }
        }

        System.out.println(answer);


    }
}
