package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
문자열 집합 조합하기
X, Y, Z : 주어지는 문자열
K : 선택하려는 문자 갯수
 */
public class B25328 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String X, Y, Z;
        int K;
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<>();
        X = br.readLine();
        Y = br.readLine();
        Z = br.readLine();
        K = Integer.parseInt(br.readLine());

        combination(X, K, 0, 0, 0, map);
        combination(Y, K, 0, 0, 0, map);
        combination(Z, K, 0, 0, 0, map);

        map.keySet().stream().filter(key -> (map.get(key) > 1)).sorted().forEach(key -> sb.append(key).append("\n"));
        if (!"".equals(sb.toString())) {
            System.out.println(sb);
        } else {
            System.out.println(-1);
        }
    }

    public static void combination(String str, int K, int cnt, int bitmask, int cur, Map<String, Integer> map) {
        if (cnt == K) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if ((bitmask & (1 << i)) == (1 << i)) {
                    sb.append(str.charAt(i));
                }
            }
            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
            return;
        }

        for (int i = cur; i < str.length(); i++) {
            combination(str, K, cnt + 1, bitmask | (1 << i), i + 1, map);
        }

    }

}
