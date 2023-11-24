package level.two;

public class n진수_게임 {
    class Solution {
        public String solution(int n, int t, int m, int p) {
            StringBuilder answer = new StringBuilder();;
            int k = m*t;

            StringBuilder sb = new StringBuilder("0");
            int i=1;
            while(sb.length()<k){
                sb.append(Integer.toString(i++,n));
            }

            String str = sb.toString().toUpperCase();
            int j =0;
            while(j<t){
                int cur = (p-1)+(m*j++);
                answer.append(str.charAt(cur));
            }

            System.out.println(sb);
            return answer.toString();
        }


    }
}
