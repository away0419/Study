package level.one;

import java.util.HashSet;
import java.util.Set;

public class 키패드_누르기 {
    class Solution {
        public static class Info{
            int x;
            int y;
            public Info(int x, int y){
                this.x = x;
                this.y = y;
            }

            public void set(int x, int y){
                this.x = x;
                this.y = y;
            }
        }

        public String solution(int[] numbers, String hand) {

            StringBuilder sb = new StringBuilder();
            Set<Integer> lset = new HashSet();
            Set<Integer> rset = new HashSet();
            int[][] arr = new int[4][3];
            Info left = new Info(3,0);
            Info right = new Info(3,2);


            lset.add(1);
            lset.add(4);
            lset.add(7);
            rset.add(3);
            rset.add(6);
            rset.add(9);

            for(int i=0; i<numbers.length; i++){
                if(lset.contains(numbers[i])){
                    left.set(numbers[i]/3, 0);
                    sb.append("L");
                    continue;
                }

                if(rset.contains(numbers[i])){
                    right.set(numbers[i]/3-1,2);
                    sb.append("R");
                    continue;
                }
                if(numbers[i]==0){
                    numbers[i] = 10;
                }
                int x = numbers[i]/3;
                int y = 1;

                int rleng = Math.abs(x-right.x) + Math.abs(y-right.y);
                int lleng = Math.abs(x-left.x) + Math.abs(y-left.y);

                if(rleng>lleng){
                    left.set(x,y);
                    sb.append("L");
                }else if(rleng<lleng){
                    right.set(x,y);
                    sb.append("R");
                }else{
                    if("right".equals(hand)){
                        right.set(x,y);
                        sb.append("R");
                    }else{
                        left.set(x,y);
                        sb.append("L");
                    }
                }


            }

            return sb.toString();
        }
    }
}
