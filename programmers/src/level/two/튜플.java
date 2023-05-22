package level.two;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class 튜플 {
    class Solution {
        public int[] solution(String s) {
            Set<String> set = new HashSet<>();
            s = s.substring(2,s.length()-2);
            String arr[] = s.split("\\},\\{");
            int[] answer = new int[arr.length];

            Arrays.sort(arr, (o1, o2) ->{
                return o1.length() - o2.length();
            });


            for(int i=0; i<arr.length; i++){
                String str[] = arr[i].split(",");

                for(int j=0; j<str.length; j++){
                    if(set.add(str[j])){
                        answer[i] = Integer.parseInt(str[j]);
                        break;
                    }
                }

            }

            return answer;
        }
    }
}
