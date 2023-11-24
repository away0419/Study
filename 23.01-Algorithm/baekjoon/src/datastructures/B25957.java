package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
단어 우월 효과
map : 단어를 넣을 맵

 */
public class B25957 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String word = br.readLine();
            map.put(strSorted(word), word);
        }
        int M = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            String word = strSorted(st.nextToken());
            if (map.containsKey(word)) {
                sb.append(map.get(word)).append(" ");
            }
        }
        System.out.println(sb);
    }

    public static String strSorted(String word) {
        char[] chs = word.toCharArray();
        if(chs.length>1){
            Arrays.sort(chs,1, chs.length-1);
        }
        return String.valueOf(chs);
//        return word.chars().mapToObj(c -> (char) c).sorted().map(String::valueOf).collect(Collectors.joining());
    }
}
