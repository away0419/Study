package level.one;

import java.util.HashMap;

public class 성격_유형_검사하기 {
    class Solution {
        public String solution(String[] survey, int[] choices) {
            HashMap<Character,Integer> map = new HashMap<Character,Integer>();
            map.put('R',0);
            map.put('T',0);
            map.put('A',0);
            map.put('N',0);
            map.put('J',0);
            map.put('M',0);
            map.put('C',0);
            map.put('F',0);

            for(int i=0; i<survey.length; i++){


                if(choices[i]/4==0){
                    int score = 4-choices[i];
                    map.put(survey[i].charAt(0),map.get(survey[i].charAt(0))+score);
                }else{
                    int score = choices[i]%4;
                    map.put(survey[i].charAt(1),map.get(survey[i].charAt(1))+score);
                }
            }

            StringBuilder sb = new StringBuilder();

            if(map.get('T')>map.get('R')){
                sb.append("T");
            }else{
                sb.append("R");
            }

            if(map.get('F')>map.get('C')){
                sb.append("F");
            }else{
                sb.append("C");
            }

            if(map.get('M')>map.get('J')){
                sb.append("M");
            }else{
                sb.append("J");
            }

            if(map.get('N')>map.get('A')){
                sb.append("N");
            }else{
                sb.append("A");
            }




            return sb.toString();
        }
    }
}
