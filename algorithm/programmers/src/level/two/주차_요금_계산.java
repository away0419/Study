package level.two;

import java.util.HashMap;
import java.util.Map;

public class 주차_요금_계산 {
    class Solution {
        public int[] solution(int[] fees, String[] records) {
            Map<String,String> recordMap = new HashMap<>();
            Map<String,Integer> sumTimeMap = new HashMap<>();
            Map<String,Integer> priceMap = new HashMap<>();

            for(int i=0; i<records.length; i++){
                String[] strs = records[i].split(" ");

                if(recordMap.containsKey(strs[1])){
                    int inTime = changeTime(recordMap.get(strs[1]));
                    int outTime = changeTime(strs[0]);
                    int stayTime = outTime - inTime;

                    sumTimeMap.put(strs[1], sumTimeMap.getOrDefault(strs[1],0)+stayTime);
                    recordMap.remove(strs[1]);
                    continue;
                }

                recordMap.put(strs[1],strs[0]);

            }

            int outTime = changeTime("23:59");
            for(String key : recordMap.keySet()){
                int inTime = changeTime(recordMap.get(key));
                int stayTime = outTime -inTime;
                sumTimeMap.put(key,sumTimeMap.getOrDefault(key,0) + stayTime);
            }


            for(String key : sumTimeMap.keySet()){
                int price = price(fees, sumTimeMap.get(key));
                priceMap.put(key,price);
            }

            int[] answer = priceMap.keySet().stream().sorted().mapToInt(key-> priceMap.get(key).intValue()).toArray();

            return answer;
        }

        public int changeTime(String time){
            String[] strs = time.split(":");
            return Integer.parseInt(strs[1]) + 60*Integer.parseInt(strs[0]);
        }

        public int price(int[] fees, float stayTime){
            int price = fees[1];
            stayTime = Math.max(stayTime - fees[0], 0);
            price += (int)Math.ceil(stayTime/fees[2]) * fees[3];

            return price;
        }
    }
}
