package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;

public class B2143 {
    static long cnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long t = Long.parseLong(br.readLine());
        Map<Long, Long> mapA = new HashMap<>();
        Map<Long, Long> mapB = new HashMap<>();
        int n = Integer.parseInt(br.readLine());
        long[] a = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine());
        long[] b = new long[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            b[i] = Long.parseLong(st.nextToken());
        }

        for (int i = 0; i < n; i++) {
            long av = 0L;
            for (int j = i; j < n; j++) {
                av += a[j];
                mapA.put(av, mapA.getOrDefault(av, 0L) + 1);
            }
        }

        for (int i = 0; i < m; i++) {
            long bv = 0L;
            for (int j = i; j < m; j++) {
                bv += b[j];
                mapB.put(bv, mapB.getOrDefault(bv, 0L) + 1);
            }
        }


        mapA.forEach((key, value) -> {
            long diff = t - key;
            long bvalue = mapB.getOrDefault(diff, 0L);
            cnt += bvalue * value;
        });

        System.out.println(cnt);
    }
}
