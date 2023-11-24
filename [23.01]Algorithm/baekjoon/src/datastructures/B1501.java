package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/*
영어 읽기
N : 사전 단어 갯수
M : 해석할 문장 수

Map<String, Map<String,Integer>> feMap : 첫글자, 마지막 글자

 */
public class B1501 {
    static int N, M;
    static Map<String, Map<String, Integer>> feMap;
    static Map<String, Integer> solMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        feMap = new HashMap<>();
        solMap = new HashMap<>();
        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            char[] charArr = br.readLine().toCharArray();

            if (charArr.length > 2) {
                String fe = Character.toString(charArr[0]) + Character.toString(charArr[charArr.length - 1]);
                String midStr = IntStream.range(1, charArr.length - 1)
                        .map(j -> charArr[j])
                        .sorted()
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                Map<String, Integer> midMap = feMap.getOrDefault(fe, new HashMap<String, Integer>());
                midMap.put(midStr, midMap.getOrDefault(midStr, 0) + 1);
                feMap.put(fe, midMap);
            } else {
                solMap.put(String.valueOf(charArr), 1);
            }

        }

        StringBuilder sb = new StringBuilder();
        M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int sum = 1;
            while (st.hasMoreTokens()) {
                char[] charArr = st.nextToken().toCharArray();
                int cnt =0;
                if (charArr.length > 2) {
                    String fe = Character.toString(charArr[0]) + charArr[charArr.length - 1];
                    String midStr = IntStream.range(1, charArr.length - 1)
                            .map(j -> charArr[j])
                            .sorted()
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();
                    Map<String, Integer> midMap = feMap.getOrDefault(fe, new HashMap<String, Integer>());
                    cnt = midMap.getOrDefault(midStr, 0);
                }else{
                    cnt = solMap.getOrDefault(String.valueOf(charArr), 0);
                }
                sum *= cnt;
            }
            sb.append(sum).append("\n");
        }

        System.out.println(sb);
    }
}
