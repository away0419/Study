package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class B25393 {
    static int N, Q;
    static Map<Integer, TreeSet<Integer>> smap, emap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        smap = new HashMap<>();
        emap = new HashMap<>();
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            TreeSet<Integer> sset = smap.getOrDefault(s, new TreeSet<>());
            sset.add(e);
            smap.put(s, sset);

            TreeSet<Integer> eset = emap.getOrDefault(e, new TreeSet<>());
            eset.add(s);
            emap.put(e, eset);


        }

        Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            if(!smap.containsKey(s) || !emap.containsKey(e)){
                sb.append(-1).append("\n");
            }else {
                TreeSet<Integer> sset = smap.get(s);
                Integer max = sset.ceiling(e);
                if(max == null){
                    sb.append(-1).append("\n");
                    continue;
                }else if(max==e){
                    sb.append(1).append("\n");
                    continue;
                }

                TreeSet<Integer> eset = emap.get(e);
                Integer min = eset.floor(s);
                if(min == null){
                    sb.append(-1).append("\n");
                    continue;
                }

                sb.append(2).append("\n");
            }

        }
        System.out.println(sb);
    }

}