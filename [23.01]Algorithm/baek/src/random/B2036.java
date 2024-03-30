package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B2036 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        List<Long> plusList = new ArrayList<>();
        List<Long> minusList = new ArrayList<>();
        long answer = 0;
        int zeroSize = 0;

        for (int i = 0; i < N; i++) {
            long a = Long.parseLong(br.readLine());

            if (a > 0) {
                plusList.add(a);
            } else if (a < 0) {
                minusList.add(a);
            } else {
                zeroSize++;
            }
        }
        Collections.sort(plusList);
        Collections.sort(minusList);

        int plusListSize = plusList.size();
        int minusListSize = minusList.size();

        for (int i = plusListSize - 1; i > 0; i -= 2) {
            long a = plusList.get(i);
            long b = plusList.get(i-1);
            answer += Math.max(a+b, a*b);
        }

        if (plusListSize % 2 == 1) {
            answer += plusList.get(0);
        }

        for (int i = 0; i < minusListSize - 1; i += 2) {
            answer += minusList.get(i) * minusList.get(i + 1);
        }

        if (minusListSize % 2 == 1) {
            if (zeroSize == 0) {
                answer += minusList.get(minusListSize - 1);
            }
        }

        System.out.println(answer);
    }
}
