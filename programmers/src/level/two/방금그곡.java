package level.two;

import java.util.*;

public class 방금그곡 {
    class Solution {

        public class Info implements Comparable<Info>{
            int startTime;
            int playTime;
            String melodia;
            String title;

            public Info(int startTime, int playTime, String title, String melodia){
                this.startTime = startTime;
                this.playTime = playTime;
                this.title = title;
                this.melodia = melodia;
            }

            public int compareTo(Info info){
                if(info.playTime == this.playTime){
                    return this.startTime - info.startTime;
                }
                return info.playTime - this.playTime;
            }
        }

        public String solution(String m, String[] musicinfos) {
            PriorityQueue<Info> pq = new PriorityQueue<>();
            m = transforMelodia(m);

            for(int i=0; i<musicinfos.length; i++){
                Info info = makeInfo(musicinfos[i]);
                int idx = info.melodia.indexOf(m);

                if(idx != -1){
                    pq.add(info);
                }
            }

            if(pq.isEmpty()){
                return "(None)";
            }

            Info info = pq.poll();

            return info.title;
        }

        // 음악 정보를 info로 변환하는 함수
        public Info makeInfo(String musicinfo){
            String[] strs = musicinfo.split(",");

            //재생 시간
            int startTime = makeTime(strs[0]);
            int endTime = makeTime(strs[1]);
            int playTime = endTime - startTime;

            //멜로디
            strs[3] = transforMelodia(strs[3]);
            int leng = strs[3].length();
            int share = playTime/leng;
            int remainder = playTime%leng;
            StringBuilder melodia = new StringBuilder();

            for(int i=0; i<share; i++){
                melodia.append(strs[3]);
            }

            melodia.append(strs[3].substring(0,remainder));

            return new Info(startTime, playTime, strs[2], melodia.toString());
        }

        //시간 -> 분 함수
        public int makeTime(String timeStr){
            String[] times = timeStr.split(":");
            int time = Integer.parseInt(times[1]);
            time += Integer.parseInt(times[0]) * 60;

            return time;
        }

        //멜로디 변환
        public String transforMelodia(String melodia){
            StringBuilder sb = new StringBuilder();
            Deque<Character> dq = new ArrayDeque<>();
            Map<Character,Character> map = new HashMap<>();

            map.put('A','H');
            map.put('C','I');
            map.put('D','J');
            map.put('F','K');
            map.put('G','L');
            map.put('E','N');

            for(int i=0; i<melodia.length(); i++){
                if(melodia.charAt(i) != '#'){
                    dq.add(melodia.charAt(i));
                    continue;
                }

                dq.add(map.get(dq.pollLast()));
            }

            while(!dq.isEmpty()){
                sb.append(dq.poll());
            }

            return sb.toString();
        }

    }
}
