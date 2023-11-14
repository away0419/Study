package level.two;

import java.util.HashMap;
import java.util.Map;

public class 뉴스_클러스터링 {
    class Solution {
        public int solution(String str1, String str2) {
            int answer = 0;
            int min = 0;
            int max = 0;
            Map<String,Integer> map1 = new HashMap<>();
            Map<String,Integer> map2 = new HashMap<>();
            int k1 = init(str1.toUpperCase(), map1);
            int k2 = init(str2.toUpperCase(), map2);

            max = k1+k2;

            for(String key : map1.keySet()){


                if(map2.containsKey(key)){
                    int k = Math.min(map2.get(key), map1.get(key));
                    min += k;
                    max -= k;
                }
            }

            if(min==0 && max==0){
                return 65536;
            }

            answer = (int)(min/(double)max*65536);

            return answer;
        }

        public int init(String word,  Map<String,Integer> map){
            int res =0;
            String regex = "[A-Z]{2}";

            for(int i=0; i<word.length()-1; i++){
                String str = word.substring(i,i+2);
                if(str.matches(regex)){
                    res++;
                    map.put(str, map.getOrDefault(str,0)+1);
                }
            }

            return res;
        }
    }
}
