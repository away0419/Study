package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B1107 {
    static int channel;
    static int push;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int target = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        int digit = (int) Math.log10(target) + 1;
        if (target == 0) {
            digit = 1;
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        if (M != 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                int index = list.indexOf(Integer.parseInt(st.nextToken()));
                list.remove(index);
            }
        }

        channel = 100;
        push = Math.abs(channel-target);
        fn(0, 0, target, digit, list, 10 - M);

        System.out.println(push);

    }


    public static void fn(int cnt, int cur, int target, int digit, List<Integer> list, int M) {
        int diff = Math.abs(cur - target);


        if (cnt >= digit) {
            return;
        }

        for (int i = 0; i < M; i++) {
            cur *= 10;
            cur += list.get(i);
            fn(cnt + 1, cur, target, digit, list, M);
            cur /= 10;
        }

    }

}
