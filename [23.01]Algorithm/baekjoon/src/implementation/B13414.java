package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
수강 신청
 */
public class B13414 {
    static int N, K;
    static Map<String,Integer> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        K = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        map = new HashMap<>();

        int cnt = 0;
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            map.put(str, cnt++);
        }

        map.keySet().stream().sorted((o1, o2)->{
            return map.get(o1) - map.get(o2);
        }).limit(K).forEach(key -> sb.append(key).append("\n"));

        System.out.println(sb);

    }
}
