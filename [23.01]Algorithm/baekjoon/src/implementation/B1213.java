package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
팰린드롬 만들기

 */
public class B1213 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        String s = br.readLine();
        Map<Character, Integer> map = new HashMap<>();
        PriorityQueue<Character> pq = new PriorityQueue<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int k = map.getOrDefault(ch, 0) + 1;

            if (k % 2 == 0) {
                pq.add(ch);
                map.put(ch, 0);
            } else {
                map.put(ch, k);
            }
        }

        boolean check = false;
        char ch = ' ';
        for (Character key :
                map.keySet()) {
            if (map.get(key) != 0) {
                if (check) {
                    System.out.println("I'm Sorry Hansoo");
                    return;
                } else {
                    check = true;
                    ch = key;
                }
            }
        }

        while (!pq.isEmpty()) {
            Character cur = pq.poll();
            sb1.append(cur);
            sb2.insert(0, cur);
        }

        if (ch != ' ') {
            sb1.append(ch);
        }

        System.out.println(sb1.append(sb2));

    }
}
