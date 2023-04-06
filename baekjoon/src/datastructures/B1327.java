package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
소트게임
N : N자리 순열
K : 뒤집어야 하는 갯수


 */
public class B1327 {
    static int K, N;
    static Set<String> set = new HashSet<>();

    public static class Info {
        int cnt;
        String str;

        public Info(int cnt, String str) {
            this.cnt = cnt;
            this.str = str;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        set = new HashSet<>();
        String sort = "";
        String str = "";
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            sort += (i + 1);
            str += Integer.parseInt(st.nextToken());
        }

        Queue<Info> q = new ArrayDeque<>();
        q.add(new Info(0, str));
        set.add(str);
        while (!q.isEmpty()) {
            Info info = q.poll();
            String s = info.str;
            if (sort.equals(s)) {
                System.out.println(info.cnt);
                return;
            }

            for (int i = 0; i <= N - K; i++) {
                String ss = s.substring(0, i);
                StringBuilder sb = new StringBuilder();
                int end = K + i;
                for (int j = i; j < end; j++) {
                    sb.insert(0, s.charAt(j));
                }
                ss += sb.toString();
                ss += s.substring(end);
                if (set.contains(ss)) {
                    continue;
                }
                set.add(ss);
                q.add(new Info(info.cnt + 1, ss));
            }

        }

        System.out.println(-1);


    }
}
