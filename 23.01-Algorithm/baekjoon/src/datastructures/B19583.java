package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
S : 시작 시간
E : 끝낸 시간
Q : 스트리밍 끝낸 시간
set : 출입 체크용 set
 */
public class B19583 {
    static int S, E, Q, answer;
    static Set<String> set;

    public static int trance(String date) {
        int res = 0;
        String s[] = date.split(":");

        res = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);

        return res;
    }

    public static void fn(String date, String name) {
        int res = trance(date);
        if (res <= S) {
            set.add(name);
        } else if (E <= res && res <= Q) {
            if (set.contains(name)) {
                set.remove(name);
                answer++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        S = trance(st.nextToken());
        E = trance(st.nextToken());
        Q = trance(st.nextToken());
        set = new HashSet<>();
        String str = br.readLine();
        while (str != null && !"".equals(str)) {
            String s[] = str.split(" ");
            fn(s[0], s[1]);
            str = br.readLine();
        }

        System.out.println(answer);
    }

}
