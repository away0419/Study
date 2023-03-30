package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
자바의 형변환
N : 클래스 수
map : 클래스 번호
parent[] : 부모
 */
public class B25601 {
    static int N, parent[];
    static Map<String, Integer> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        map = new HashMap<>();
        int cnt = 0;
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            String str1 = st.nextToken();
            String str2 = st.nextToken();

            if (!map.containsKey(str1)) {
                map.put(str1, cnt++);
            }
            if (!map.containsKey(str2)) {
                map.put(str2, cnt++);
            }
            int value1 = map.get(str1);
            int value2 = map.get(str2);

            parent[value1] = value2;

        }

        st = new StringTokenizer(br.readLine());
        String str1 = st.nextToken();
        String str2 = st.nextToken();
        int value1 = map.get(str1);
        int value2 = map.get(str2);
        boolean check1 = find(value1, value2);
        boolean check2 = find(value2, value1);

        if(check1 || check2){
            System.out.println(1);
        }else{
            System.out.println(0);
        }
    }

    public static boolean find(int value1, int value2){
        if(parent[value1] == value1){
            return false;
        }
        else if(parent[value1] == value2){
            return true;
        }
        return find(parent[value1], value2);
    }
}
