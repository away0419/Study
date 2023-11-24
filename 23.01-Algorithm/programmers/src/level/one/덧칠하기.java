package level.one;

public class 덧칠하기 {
    class Solution {
        public int solution(int n, int m, int[] section) {
            int answer = 0;
            int start = 0;

            for(int i =0; i<section.length; i++){
                if(start<=section[i]){
                    start=section[i]+m;
                    answer++;
                }
            }

            return answer;
        }
    }
}
