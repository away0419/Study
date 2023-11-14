package level.two;

import java.util.Stack;

public class 택배_배달과_수거하기 {
    class Solution {
        public class Info{
            int idx;
            int box;

            public Info(int idx, int box){
                this.idx = idx;
                this.box = box;
            }
        }

        public long solution(int cap, int n, int[] deliveries, int[] pickups) {
            long answer = 0;
            Stack<Info> dstack = new Stack<>();
            Stack<Info> pstack = new Stack<>();

            for(int i=0; i<n; i++){
                if(deliveries[i]!=0){
                    dstack.add(new Info(i+1, deliveries[i]));
                }

                if(pickups[i]!=0){
                    pstack.add(new Info(i+1, pickups[i]));
                }
            }

            while(!dstack.isEmpty() || !pstack.isEmpty()){
                int dres = fn(dstack, cap);
                int pres = fn(pstack, cap);
                answer += Math.max(dres, pres) * 2;
            }


            return answer;
        }

        public int fn (Stack<Info> stack, int n){

            if(stack.isEmpty()){
                return 0;
            }

            Info info = stack.pop();
            int max = info.idx;

            while(n>0){

                if(n-info.box<0){
                    info.box -= n;
                    stack.add(info);
                    break;
                }

                n-=info.box;

                if(stack.isEmpty() || n == 0){
                    break;
                }

                info = stack.pop();

            }

            return max;
        }

    }
}
