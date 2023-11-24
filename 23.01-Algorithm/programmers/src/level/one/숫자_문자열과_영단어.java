package level.one;

public class 숫자_문자열과_영단어 {
    class Solution {
        public int solution(String s) {
            int answer = 0;
            String[] word = {"zero", "one", "two", "three", "four", "five", "six",
                    "seven", "eight", "nine"};

            for(int i=0; i<=9; i++){
                s = s.replace(word[i], String.valueOf(i));
            }

            return Integer.parseInt(s);
        }
    }
}
