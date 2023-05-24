package level.two;

import java.util.ArrayDeque;
import java.util.Queue;

public class 두_큐_합_같게_만들기 {
    class Solution {
        public int solution(int[] queue1, int[] queue2) {
            int max = queue1.length*4;
            double sum1=0;
            double sum2=0;

            Queue<Double> q1 = new ArrayDeque<>();
            Queue<Double> q2 = new ArrayDeque<>();

            for(int i=0; i< queue1.length; i++){
                sum1 += queue1[i];
                sum2 += queue2[i];

                q1.add((double)queue1[i]);
                q2.add((double)queue2[i]);
            }

            int cnt =0;
            while(sum1 != sum2 && cnt<max){
                cnt++;

                if(sum1>sum2){
                    double k = q1.poll();
                    sum1-= k;
                    sum2+= k;
                    q2.add(k);
                }else{
                    double k = q2.poll();
                    sum1+= k;
                    sum2-= k;
                    q1.add(k);
                }
            }

            if(cnt == max){
                return -1;
            }

            return cnt;
        }
    }
}
