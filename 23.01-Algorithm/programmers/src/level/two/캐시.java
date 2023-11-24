package level.two;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class 캐시 {
    class Solution {
        public int solution(int cacheSize, String[] cities) {
            int answer = 0;
            Queue<String> q = new ArrayDeque<>();
            Set<String> set = new HashSet<>();

            if(cacheSize==0){
                return cities.length*5;
            }
            for(int i=0; i<cities.length; i++){
                String str = cities[i].toUpperCase();
                if(set.add(str)){
                    answer+=5;

                    if(set.size()>cacheSize){
                        String last = q.poll();
                        set.remove(last);

                    }

                    q.add(str);

                }else{
                    answer+=1;


                    int size = q.size();
                    for(int j=0; j<size; j++){
                        String cur =q.poll();

                        if(cur.equals(str)){
                            continue;
                        }

                        q.add(cur);
                    }

                    q.add(str);

                }
            }

            return answer;
        }
    }
}
