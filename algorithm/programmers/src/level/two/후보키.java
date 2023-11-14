package level.two;

import java.util.*;

public class 후보키 {
    class Solution {

        public int solution(String[][] relation) {
            int answer =0;
            int n = relation[0].length;
            int visit = 0;
            int cnt = 0;
            int cur = 0;
            PriorityQueue<Integer> pq = new PriorityQueue<>();

            combination(cur, cnt, visit, n, relation, pq);
            answer = culcolration(pq);

            return answer;
        }

        public int culcolration(PriorityQueue<Integer> pq){
            int res = 0;
            Queue<Integer> q = new ArrayDeque<>();

            while(!pq.isEmpty()){
                q.add(pq.poll());
            }

            while(!q.isEmpty()){
                int cur = q.poll();
                res++;
                // System.out.println(Integer.toBinaryString(cur));
                int cnt = q.size();
                for(int i=0; i<cnt; i++){
                    int next = q.poll();

                    if((next & cur) == cur){
                        continue;
                    }

                    // System.out.println(Integer.toBinaryString(cur) + " " + Integer.toBinaryString(next));
                    q.add(next);
                }
            }

            return res;
        }

        public boolean checkKey(int visit, String[][] relation, PriorityQueue<Integer> pq ){
            int row = relation.length;
            int colum = relation[0].length;
            Set<String> set = new HashSet<>();

            for(int i=0; i<row; i++){
                StringBuilder sb = new StringBuilder();

                for(int j=0; j<colum; j++){
                    if(((visit>>j) & 1 ) == 1){
                        sb.append(relation[i][j]).append(" ");
                    }
                }

                if(!set.add(sb.toString())){
                    return false;
                }
            }
            pq.add(visit);

            return true;
        }

        public void combination(int cur, int cnt, int visit, int n, String[][] relation, PriorityQueue<Integer> pq){
            if(cnt != 0){

                boolean res = checkKey(visit, relation, pq);
                if(res){
                    return;
                }
            }

            if(cnt == n){
                return;
            }

            for(int i= cur; i<n; i++){
                combination(i+1, cnt+1, visit | (1 << i), n, relation, pq);
            }

        }
    }
}
