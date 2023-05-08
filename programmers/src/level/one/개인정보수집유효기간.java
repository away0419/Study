package level.one;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 개인정보수집유효기간 {
    /*
출력 : 파기해야 할 개인정보의 번호를 오름차순으로 1차원 정수 배열로 return
기능1 : 약관 종류와 기간 Map에 담아 반환
기능2 : 현재 날짜와 문서를 받아 비교 후 번호 반환
기능3 : 문서 날짜에 기간을 더한 날짜 구하기
기능4 : 날짜를 숫자로 변환 후 반환

*/

    class Solution {
        public int[] solution(String today, String[] terms, String[] privacies) {
            List<Integer> passList = new ArrayList<>();
            Map<String,Integer> termMap = check(terms);
            int[] tdate = fn4(today);

            for(int i=0; i<privacies.length; i++){
                int res = fn2(tdate, privacies[i], termMap, i+1);
                if(res != -1){
                    passList.add(res);
                }
            }

            int[] answer = passList.stream().mapToInt(Integer::intValue).sorted().toArray();

            return answer;
        }

        //약관 종류 기간 Map 생성
        public Map<String,Integer> check(String[] terms){
            Map<String,Integer> map = new HashMap<>();

            for(String term : terms){
                String[] strs = term.split(" ");
                map.put(strs[0], Integer.parseInt(strs[1]));
            }

            return map;

        }

        //현재 날짜와 문서를 받아 비교 후 번호 반환
        public int fn2(int[] tdate, String privacie, Map<String,Integer> termMap, int no){
            String[] strs = privacie.split(" ");
            int[] pdate = fn3(strs[0], termMap.get(strs[1]));

            if(pdate[0] < tdate[0]){
                return no;
            }

            if(pdate[0] == tdate[0] && pdate[1] < tdate[1]){
                return no;
            }

            if(pdate[0] == tdate[0] && pdate[1] == tdate[1] && pdate[2] <= tdate[2]){
                return no;
            }

            return -1;

        }

        //문서 날짜에 기간을 더한 날짜 구한 후 반환
        public int[] fn3(String date, int month){
            String[] strs = date.split("\\.");
            int[] res = fn4(date);

            res[1] += month;
            if(res[1]>=12 && res[1]%12==0){
                res[0] += res[1]/12 -1;
                res[1] = 12;
            }else{
                res[0] += res[1]/12;
                res[1] %= 12;
            }

            return res;
        }

        //날짜를 숫자로 변환 후 반환
        public int[] fn4(String date){
            int[] res = new int[3];
            String[] strs = date.split("\\.");

            for(int i=0; i<3; i++){
                res[i] = Integer.parseInt(strs[i]);
            }

            return res;
        }


    }
}
