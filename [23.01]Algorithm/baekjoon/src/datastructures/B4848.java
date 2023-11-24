package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
집합 숫자 표기법
map1 : String , Integer
map2 : Integer, String
 */
public class B4848 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str[] = new String[16];
        Map<String, Integer> map = new HashMap<>();
        str[0] = "{}";
        map.put(str[0], 0);
        for (int i = 1; i < 16; i++) {
            StringBuilder sb = new StringBuilder(str[0]);
            for (int j = 1; j < i; j++) {
                sb.append(",").append(str[j]);
            }
            str[i] = "{" + sb.toString() + "}";
            map.put(str[i], i);
        }

        int t = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        while (t-- > 0) {
            int a = map.get(br.readLine());
            int b = map.get(br.readLine());
            answer.append(str[a + b]).append("\n");
        }

        System.out.println(answer);

    }
}
