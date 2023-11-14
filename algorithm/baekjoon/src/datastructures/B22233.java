package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
가희와 키워드
N : 키워드 갯수
M : 쓴 글
 */
public class B22233 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Set<String> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        while (n-- > 0) {
            set.add(br.readLine());
        }

        while (m-- > 0) {
            String str[] = br.readLine().split(",");
            for (int i = 0; i < str.length; i++) {
                set.remove(str[i]);
            }
            sb.append(set.size()).append("\n");
        }

        System.out.println(sb);
    }

}
