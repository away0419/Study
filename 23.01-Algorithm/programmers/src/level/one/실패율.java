package level.one;

import java.util.Arrays;

public class 실패율 {
    class Solution {
        public class Info implements Comparable<Info>{
            int no;
            double fail;

            public Info(int no, double fail){
                this.no = no;
                this.fail = fail;
            }

            public int compareTo(Info i){
                if(i.fail == this.fail){
                    return this.no - i.no;
                }
                return Double.compare(i.fail, this.fail);
            }

        }

        public int[] solution(int N, int[] stages) {
            double[] arr = new double[N+2];
            Info[] infos = new Info[N];
            int cnt = stages.length;
            int[] answer = new int[N];


            for(int i=0; i<cnt; i++){
                arr[stages[i]]++;
            }

            for(int i=0; i<N; i++){
                if(cnt==0){
                    infos[i] = new Info(i+1,0L);
                    continue;
                }
                double d = arr[i+1]/cnt;
                cnt -= arr[i+1];
                infos[i] = new Info(i+1,d);
            }


            Arrays.sort(infos);

            for(int i=0; i<N; i++){
                answer[i] = infos[i].no;
            }


            return answer;
        }
    }
}
