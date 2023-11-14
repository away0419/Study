package level.two;

public class 문자열_압축 {
    class Solution {
        public int solution(String s) {
            int answer = Integer.MAX_VALUE;


            for(int i=1; i<= s.length()/2 + 1; i++){
                String str = s.substring(0,i);
                int start = i;
                int sum =0;
                int equalsCnt = 1;

                while(start+i <= s.length()){
                    String next = s.substring(start, start+i);
                    start += i;

                    if(next.equals(str)){
                        equalsCnt++;
                        continue;
                    }

                    if(equalsCnt!=1){
                        while(equalsCnt>0){
                            equalsCnt/=10;
                            sum++;
                        }
                    }

                    equalsCnt = 1;
                    sum += i;
                    str = next;
                }

                if(equalsCnt!=1){
                    while(equalsCnt>0){
                        equalsCnt/=10;
                        sum++;
                    }
                }

                sum += i + s.length() - start;

                answer = Math.min(sum, answer);
            }


            return answer;
        }
    }
}
