package level.two;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 압축 {
    class Solution {
        public int[] solution(String msg) {
            int value = 27;
            int leng = msg.length();
            List<Integer> list = new ArrayList<>();
            Map<String,Integer> pv = new HashMap<>();

            for(int i=0; i<26; i++){
                int k = 'A'+i;
                char ch = (char)k;
                pv.put(Character.toString(ch), i+1);
            }


            for(int i=0; i<leng; i++){
                int j = i;
                StringBuilder cur = new StringBuilder(Character.toString(msg.charAt(i)));
                int curValue = 0;

                while(pv.containsKey(cur.toString())){
                    curValue = pv.get(cur.toString());
                    if(++j>=leng){
                        break;
                    }
                    cur = cur.append(msg.charAt(j));
                }

                i = j-1;

                pv.put(cur.toString(), value++);
                list.add(curValue);



            }
            int answer[] = list.stream().mapToInt(Integer::intValue).toArray();
            return answer;
        }
    }
}
