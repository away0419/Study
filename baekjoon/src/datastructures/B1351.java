package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
무한 수열
 */
public class B1351 {
    static long N;
    static int P, Q;
    static Map<Long, Long> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Long.parseLong(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        map = new HashMap<>();

        System.out.println(fn(N));
    }

    public static long fn(long cur) {
        if (cur == 0) {
            return 1L;
        }
        if (map.containsKey(cur)) {
            return map.get(cur);
        }
        map.put(cur, fn(cur / P) + fn(cur / Q));

        return map.get(cur);
    }

}
