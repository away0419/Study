package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class B2036_1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Long> plusList = new ArrayList<>();
        List<Long> minusList = new ArrayList<>();
        int N = Integer.parseInt(br.readLine());
        boolean zeroCheck = false;
        long answer = 0;

        for (int i = 0; i < N; i++) {
            long a = Long.parseLong(br.readLine());
            if (a > 0) {
                plusList.add(a);
            } else if (a < 0) {
                minusList.add(a);
            } else {
                zeroCheck = true;
            }
        }

        Collections.sort(plusList, Collections.reverseOrder());
        Collections.sort(minusList);


        int plusSize = 0;
        for (; plusSize < plusList.size() - 1; plusSize += 2) {
            long product = plusList.get(plusSize) * plusList.get(plusSize + 1);
            long sum = plusList.get(plusSize) + plusList.get(plusSize + 1);
            answer += Math.max(product, sum);
        }

        if (plusSize != plusList.size()) {
            answer += plusList.get(plusList.size() - 1);
        }


        int minusSize = 0;
        for (; minusSize < minusList.size() - 1; minusSize += 2) {
            answer += minusList.get(minusSize) * minusList.get(minusSize + 1);
        }

        if (minusSize != minusList.size() && !zeroCheck) {
            answer += minusList.get(minusList.size() - 1);
        }

        System.out.println(answer);

    }
}
