package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
마니또
N : 사람 수
 */
public class B5107 {

    static int N, parent[];
    static boolean visit[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        long tc = 1;
        while (true) {
            N = Integer.parseInt(br.readLine());
            if (N == 0) {
                break;
            }
            parent = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
            Map<String, Integer> map = new HashMap<>();
            visit = new boolean[N];
            int cnt = 0;
            int answer = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                String str1 = st.nextToken();
                String str2 = st.nextToken();

                if (!map.containsKey(str1)) {
                    map.put(str1, cnt++);
                }
                if (!map.containsKey(str2)) {
                    map.put(str2, cnt++);
                }
                int value1 = find(parent[map.get(str1)]);
                int value2 = find(parent[map.get(str2)]);

                if(value1 != value2){
                    union(value1, value2);
                }else{
                    answer++;
                }

            }
            sb.append(tc++).append(" ").append(answer).append("\n");
        }
        System.out.println(sb);
    }

    public static int find(int value){
        if(parent[value] == value){
            return value;
        }
        return parent[value] = find(parent[value]);
    }

    public static void union(int a, int b){
        if(a<b){
            parent[a] = b;
        }else {
            parent[b] = a;
        }
    }
}
