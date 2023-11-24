package datastructures;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
졸업 사진

 */
public class B23349 {
    static class table {
        int start, end;

        table(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        HashSet<String> names = new HashSet<>();
        TreeMap<String, int[]> places = new TreeMap<>();
        TreeMap<String, ArrayList<table>> reserve = new TreeMap<>();
        int n = Integer.parseInt(bf.readLine());
        StringTokenizer st = null;
        while (n-- > 0) {
            st = new StringTokenizer(bf.readLine());
            String name = st.nextToken();
            if (names.contains(name)) continue;
            String place = st.nextToken();
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            names.add(name);
            ArrayList<table> tmp = reserve.getOrDefault(place, new ArrayList<>());
            tmp.add(new table(start, end));
            reserve.put(place, tmp);
        }
        int max = 0;
        String maxPlace = null;

        for (String s : reserve.keySet()) {
            int[] time = places.getOrDefault(s, new int[50001]);
            for (table t : reserve.get(s)) {
                for (int i = t.start; i < t.end; i++) {
                    time[i]++;
                    if (max < time[i]) {
                        maxPlace = s;
                        max = time[i];
                    }
                }
            }
            places.put(s, time);
        }
        int[] tmp = places.get(maxPlace);
        boolean flag = false;
        int start = 0, end = 0;
        for (int i = 1; i < 50001; i++) {
            if (tmp[i] == max && !flag) {
                start = i;
                flag = true;
            } else if (tmp[i] < max && flag) {
                end = i;
                flag = false;
                break;
            }
        }
        if (flag) end = 50000;
        bw.write(maxPlace + " " + start + " " + end + "\n");
        bf.close();
        bw.flush();
        bw.close();
    }
}
