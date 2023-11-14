package random;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class B17436 {
    static long answer, m;
    static int n;
    static int[] p;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextLong();
        p = new int[n];

        for (int i = 0; i < n; i++) {
            p[i] = scanner.nextInt();
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                List<Integer> v = new ArrayList<>();
                v.add(j);
                bt(1, j, i, v);
            }
        }

        System.out.println(answer);
    }

    public static void bt(int size, int start, int l, List<Integer> v) {
        if (size == l) {
            if (l % 2 == 1) {
                long temp = 1;
                for (int i = 0; i < l; i++) {
                    temp *= p[v.get(i) - 1];
                }
                answer += m / temp;
            } else {
                long temp = 1;
                for (int i = 0; i < l; i++) {
                    temp *= p[v.get(i) - 1];
                }
                answer -= m / temp;
            }
            return;
        }

        for (int i = start + 1; i <= n; i++) {
            v.add(i);
            bt(size + 1, i, l, v);
            v.remove(v.size() - 1);
        }
    }
}