package random;

import java.util.Scanner;

public class B1424 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        long l = sc.nextLong();
        long c = sc.nextLong();

        long ans = Long.MAX_VALUE;
        for (long k = (c + 1) / (l + 1); k > 0; k--) {
            if (k % 13 == 0) continue;
            long r = n % k;
            if (r > 0) {
                if (r % 13 == 0 && r + 1 == k) r = 2;
                else r = 1;
            }
            ans = Math.min(ans, n / k + r);
        }

        System.out.println(ans);
    }
}