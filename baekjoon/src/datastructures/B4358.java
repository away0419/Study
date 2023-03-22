package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
생태학

 */
public class B4358 {
    static Map<String, Integer> map;
    static float cnt = 0;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        map = new HashMap<>();
        while ((str = br.readLine()) != null && !"".equals(str)) {
            map.put(str, map.getOrDefault(str, 0) + 1);
            cnt++;
        }
        StringBuilder sb = new StringBuilder();
//        if(cnt>0){
//            map.entrySet().stream().sorted((e1,e2)->{return CharSequence.compare(e1.getKey(),e2.getKey());}).forEach(e->sb.append(e.getKey()).append(" ").append(String.format("%.4f",e.getValue()/cnt*100)).append("\n"));
//        }

        Set<String> set = map.keySet();
        List<String> list = new ArrayList<>(set);
        Collections.sort(list);
        for (String s :
                list) {
            sb.append(s).append(" ").append(String.format("%.4f",map.get(s)/cnt*100)).append("\n");
        }

        System.out.println(sb);


    }

}
