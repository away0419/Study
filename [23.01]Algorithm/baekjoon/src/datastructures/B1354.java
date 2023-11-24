package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
무한 수열 2

 */
public class B1354 {
    static long N, P, Q, X, Y;
    static Map<Long, Long> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Long.parseLong(st.nextToken());
        P = Long.parseLong(st.nextToken());
        Q = Long.parseLong(st.nextToken());
        X = Long.parseLong(st.nextToken());
        Y = Long.parseLong(st.nextToken());
        map = new HashMap<>();
        long answer = fn(N);
        System.out.println(answer);
    }

    public static long fn(long value) {
        if (value <= 0) {
            return 1L;
        }
        if (map.containsKey(value)) {
            return map.get(value);
        }
        long a = value / P - X;
        long b = value / Q - Y;
        long sum = fn(a) + fn(b);
        map.put(value, sum);
        return sum;
    }
}
