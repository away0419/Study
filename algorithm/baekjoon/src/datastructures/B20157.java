package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class B20157 {
    static int N;
    static Map<Double, Integer>[] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new HashMap[4];
        for (int i = 0; i < 4; i++) {
            map[i] = new HashMap<>();
        }
        StringTokenizer st;
        int max = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            double y = Double.parseDouble(st.nextToken());

            double s = y / x;
            int idx = 0;
            if (x < 0 && y < 0) {
                idx = 3;
            } else if (x > 0 && y < 0) {
                idx = 2;
            } else if (x < 0 && y > 0) {
                idx = 1;
            }

            map[idx].put(s, map[idx].getOrDefault(s, 0) + 1);
            max = Math.max(max, map[idx].get(s));
        }
        System.out.println(max);
    }


}
