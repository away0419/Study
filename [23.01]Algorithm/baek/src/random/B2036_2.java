package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B2036_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        List<Long> minusList = new ArrayList<>();
        List<Long> plusList = new ArrayList<>();
        boolean zeroCheck = false;
        long answer = 0L;

        for (int i = 0; i < n; i++) {
            long a = Long.parseLong(br.readLine());
            if (a > 0) {
                plusList.add(a);
            } else if (a == 0) {
                zeroCheck = true;
            } else {
                minusList.add(a);
            }
        }

        Collections.sort(plusList, Collections.reverseOrder());
        Collections.sort(minusList);

        for (int i = 0; i < plusList.size() - 1; i += 2) {
            long sum = plusList.get(i) + plusList.get(i + 1);
            long product = plusList.get(i) * plusList.get(i + 1);
            answer += Math.max(product, sum);
        }

        if (plusList.size() % 2 != 0) {
            answer += plusList.get(plusList.size() - 1);
        }

        for (int i = 0; i < minusList.size() - 1; i += 2) {
            answer += minusList.get(i) * minusList.get(i + 1);
        }

        if (minusList.size() % 2 != 0 && !zeroCheck) {
            answer += minusList.get(minusList.size() - 1);
        }

        System.out.println(answer);

    }
}
