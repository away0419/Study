package level.two;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class 오픈채팅방 {
    class Solution {
        public class Log{
            String userId;
            String msg;

            public Log(String userId, String msg){
                this.userId = userId;
                this.msg = msg;
            }
        }

        public String[] solution(String[] record) {
            Queue<Log> logQ = new ArrayDeque<>();
            Map<String,String> nameMap = new HashMap<>();

            for(int i=0; i<record.length; i++){
                command(record[i], logQ, nameMap);
            }

            String[] answer = logQ.stream().map(e-> nameMap.get(e.userId)+e.msg).toArray(String[]::new);
            return answer;
        }

        public void command(String record, Queue<Log> logQ, Map<String,String> nameMap){
            String[] strs = record.split(" ");
            int command = strs[0].charAt(0);

            if(command == 'E'){
                logQ.add(new Log(strs[1], "님이 들어왔습니다."));
                nameMap.put(strs[1], strs[2]);
                return;
            }

            if(command == 'L'){
                logQ.add(new Log(strs[1], "님이 나갔습니다."));
                return;
            }

            if(command == 'C'){
                nameMap.put(strs[1], strs[2]);
                return;
            }
        }
    }
}
