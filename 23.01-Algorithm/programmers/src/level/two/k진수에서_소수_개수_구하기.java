package level.two;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class k진수에서_소수_개수_구하기 {
    class Solution {
        public int solution(int n, int k) {
            int answer = 0;
            String str = Integer.toString(n,k);

            Pattern pattern = Pattern.compile("[1-9]+");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                String t = matcher.group();
                if(check(t)){
                    answer++;
                }
            }

            return answer;
        }

        public boolean check(String s){
            long k = Long.parseLong(s);
            if(k<2){
                return false;
            }

            for(int i=2; i<=Math.sqrt(k); i++){
                if(k%i==0){
                    return false;
                }
            }

            return true;
        }


    }
}
