package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
통계학
N : 주어진 수
arr[] : 주어지는 배열
map<Integer,Integer> : 수가 나온 횟수

 */
public class B2108 {
    static int N, arr[], MAX;
    static long SUM;
    static Map<Integer, Integer> map;
    static List<Integer> list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        map = new HashMap<>();
        list = new ArrayList<>();


        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            int k = map.getOrDefault(arr[i], 0) + 1;
            if (MAX < k) {
                MAX = k;
                list.clear();
                list.add(arr[i]);
            } else if (MAX == k) {
                list.add(arr[i]);
            }
            map.put(arr[i], k);
            SUM += arr[i];
        }

        Arrays.sort(arr);
        Collections.sort(list);

        sb.append((int) Math.round(SUM / (float) N)).append("\n");
        sb.append(arr[N / 2]).append("\n");
        sb.append((list.size() > 1) ? list.get(1) : list.get(0)).append("\n");
        sb.append(arr[N - 1] - arr[0]);
        System.out.println(sb);
    }


}
