package level.two;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class 단체사진찍기 {
    class Solution {
        static int answer;

        public class Condition{
            int s;
            int e;
            int diff;
            int sign;

            public Condition(int s, int e, int diff, int sign){
                this.s = s;
                this.e = e;
                this.diff = diff;
                this.sign = sign;
            }
        }

        public int solution(int n, String[] data) {
            answer = 0;
            char[] names ={'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
            n = data.length;
            int m = names.length;
            int[] orders = new int[m];

            Map<Character, Integer> map = IntStream.range(0, names.length)
                    .boxed()
                    .collect(Collectors.toMap(i -> names[i], i -> i));

            Condition[] conditions = makeConditionArr(n, data, map);

            permutation(m, 0, 0, orders, conditions);

            return answer;
        }

        public Condition[] makeConditionArr(int n, String[] data, Map<Character, Integer> map){
            Condition[] conditions = new Condition[n];

            for(int i=0; i<n; i++){
                char[] chars = data[i].toCharArray();
                int diff = chars[4] -'0';
                int sign = 0;

                if(chars[3] == '<'){
                    sign = -1;
                }else if(chars[3] == '>'){
                    sign = 1;
                }

                conditions[i] = new Condition(map.get(chars[0]), map.get(chars[2]), diff, sign);
                // System.out.printf("%d, %d, %d \n",map.get(chars[0]), map.get(chars[2]), diff);
            }

            return conditions;
        }

        public void permutation(int m, int cnt, int visit, int[] orders, Condition[] conditions) {
            if(cnt == m){

                for(int i=0; i<conditions.length; i++){
                    int sorder = orders[conditions[i].s];
                    int eorder = orders[conditions[i].e];
                    int diff = Math.abs(sorder- eorder) -1;

                    if(conditions[i].sign<0){
                        if(conditions[i].diff <= diff){
                            return;
                        }
                    }else if(conditions[i].sign == 0){
                        if(diff !=0){
                            return;
                        }
                    }else{
                        if(conditions[i].diff >= diff){
                            return;
                        }
                    }
                }


                answer++;
                return;
            }

            for(int i=0; i<8; i++){

                if((visit >> i & 1) == 1){
                    continue;
                }

                orders[i] = cnt;
                permutation(m, cnt+1, visit | 1<<i, orders, conditions);
            }
        }
    }
}
