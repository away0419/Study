package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class B15711 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        Set<Long> visits = new HashSet<>();
        Set<Long> prime = new HashSet<>();
        Set<Long> possible = new HashSet<>();

        double max = Math.pow(10, 12) * 4;

        for (long i = 2; i*i <= max; i++) {

            if (visits.add(i)) {
                for (Long next :
                        prime) {
                    possible.add(next + i);

                }
                prime.add(i);

                for (long j = i * i; j <= max; j += i) {
                    visits.add(j);
                }
            }
        }

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            long sum = a + b;
            if (possible.contains(sum)) {
                sb.append("YES");
            } else {
                sb.append("NO");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }


}
