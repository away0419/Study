package level.one;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class 신고_결과_받기 {
    /*
기능1 : 한 유저 신고 최대 1회
기능2 : k번 이상 신고 당하면 이용 정지
기능3 : 정지 당한 유저 신고한 모든 유저에게 정지 메일 발송

누적 신고 횟수를 담을 map
해당 사람을 신고한 사람들 목록을 담은 무엇이 필요함 => set[]
한 사람이 신고한 사람 목록들 담은 무엇이 필요함 => set[]


map1 : 이름, idx
map2 : idx, 신고당한 횟수
arr[idx] : idx가 신고한 사람이 정지될 경우 +1 > answer임

*/

    class Solution {
        public int[] solution(String[] id_list, String[] report, int k) {
            int n = id_list.length;
            int[] answer = new int[n];
            Map<String,Integer> keyMap = new HashMap<String,Integer>();
            Map<Integer,Integer> cntMap = new HashMap<Integer,Integer>();

            Set<Integer>[] sets = new HashSet[n];
            Set<Integer>[] reportSets = new HashSet[n];

            for(int i=0; i<n; i++){
                keyMap.put(id_list[i],i);
                sets[i] = new HashSet<>();
                reportSets[i] = new HashSet<>();
            }

            for(int i=0; i<report.length; i++){
                String[] names = report[i].split(" ");

                int idx1 = keyMap.get(names[0]);
                int idx2 = keyMap.get(names[1]);

                if(reportSets[idx1].add(idx2)){
                    cntMap.put(idx2, cntMap.getOrDefault(idx2,0)+1);
                }

                sets[idx2].add(idx1);
            }

            for(Set<Integer> set : sets){
                if(set.size()<k){
                    continue;
                }

                for(int idx : set){
                    answer[idx]++;
                }
            }


            return answer;
        }
    }
}
