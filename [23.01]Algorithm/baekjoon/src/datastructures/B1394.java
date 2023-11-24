package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
암호

map<char, Integer> 최초로 나왔을 때 인덱스
 */
public class B1394 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        int size = str.length();
        Map<Character, Integer> map = new HashMap<>();
        int answer = 0;
        for (int i = 1; i <= size; i++) {
            char ch = str.charAt(i - 1);
            map.put(ch, map.getOrDefault(ch, i));
        }

        String word = br.readLine();

        for (int i = 0; i < word.length(); i++) {
            answer *= size % 900528;
            answer %= 900528;
            answer += map.get(word.charAt(i)) % 900528;
            answer %= 900528;
        }

        System.out.println(answer);
    }
}
