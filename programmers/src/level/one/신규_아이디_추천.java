package level.one;

public class 신규_아이디_추천 {
    class Solution {
        public String solution(String new_id) {
            String answer = "";

            //1단계
            answer = new_id.toLowerCase();

            //2단계
            answer = answer.replaceAll("[^0-9a-z\\-_\\.]", "");

            //3단계
            answer = answer.replaceAll("\\.{2,}", ".");

            //4단계
            answer = answer.replaceAll("^\\.(.*)", "$1");
            answer = answer.replaceAll("(.*)\\.$", "$1");

            //5단계
            if("".equals(answer)){
                answer="a";
            }

            //6단계
            if(answer.length()>15){
                answer = answer.substring(0,15);
            }
            answer = answer.replaceAll("(.+)\\.$", "$1");

            //7단계
            while(answer.length()<3){
                answer += answer.substring(answer.length()-1);
            }

            return answer;
        }
    }
}
