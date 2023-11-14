package level.two;

import java.util.Stack;

public class 괄호_변환 {
    class Solution {
        public String solution(String p) {

            return fn(p);
        }

        //최종 결과 값 만들어 주는 함수
        public String fn(String str){

            //조건1
            if("".equals(str)){
                return "";
            }

            //조건2
            String[] res = makeUV(str);

            //조건3
            if(check(res[0])){

                // 조건3-1
                return res[0] + fn(res[1]);
            }

            //조건4
            return update(res[0], res[1]);

        }

        //조건2 함수
        public String[] makeUV(String w){

            int[] range = new int[2];
            String[] res = new String[2];
            Stack<Character> stack = new Stack<>();

            stack.add(w.charAt(0));

            for(int i=1; i<w.length(); i++){
                char ch = w.charAt(i);

                if(ch == stack.peek()){
                    stack.add(ch);
                }else{
                    stack.pop();
                    if(stack.isEmpty()){
                        range[1] = i;
                        break;
                    }
                }

            }

            res[0] = w.substring(0, range[1]+1);
            res[1] = res[1] = w.substring(range[1]+1);

            // String str1 = w.substring(0, range[1]+1);
            // String str2 = res[1] = w.substring(range[1]+1);

            // if(str1.length() != w.length() && str1.length()>str2.length()){
            //     res[0] = str2;
            //     res[1] = str1;
            // }else{
            //     res[0] = str1;
            //     res[1] = str2;
            // }

            return res;
        }

        //조건3 함수
        public boolean check(String u){
            if(u.charAt(0) =='('){
                return true;
            }

            return false;
        }

        //조건4 함수
        public String update(String u, String v){

            //조건4-1
            String str ="(";

            //조건4-2
            str += fn(v);

            //조건4-3
            str += ")";

            //조건4-4
            str += replace(u);

            return str;
        }

        //조건4-4 함수
        public String replace(String u){
            String res = "";

            for(int i=1; i<u.length()-1; i++){
                if(u.charAt(i) == ')'){
                    res += "(";
                }else{
                    res += ")";
                }
            }

            return res;
        }


    }
}
