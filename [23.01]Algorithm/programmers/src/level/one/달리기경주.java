package level.one;

import java.util.*;

class 달리기경주 {
    public String[] solution(String[] players, String[] callings) {
        String[] answer = new String[players.length];

        Map<String, Integer> rankMap = new HashMap<>();
        Map<Integer, String> nameMap = new HashMap<>();

        for (int i = 0; i < players.length; i++) {
            rankMap.put(players[i], i);
            nameMap.put(i, players[i]);
        }


        for (int i = 0; i < callings.length; i++) {
            int rank = rankMap.get(callings[i]);
            String preName = nameMap.get(rank - 1);

            rankMap.put(callings[i], rank - 1);
            rankMap.put(preName, rank);

            nameMap.put(rank - 1, callings[i]);
            nameMap.put(rank, preName);
        }

        for (int idx :
                nameMap.keySet()) {
            answer[idx] = nameMap.get(idx);
        }

        return answer;
    }


}
