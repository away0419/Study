package level.one;

import java.util.PriorityQueue;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
        answer = fn(targets);
        return answer;
    }

    public int fn(int[][] targets) {
        PriorityQueue<Info> pq = makepq(targets);
        int e = -1;
        int answer = 0;
        while (!pq.isEmpty()) {
            Info info = pq.poll();
            if (e <= info.s) {
                answer++;
                e = info.e;
            }else{
                if(e>info.e){
                    e=info.e;
                }

            }
        }
        return answer;
    }

    public PriorityQueue<Info> makepq(int[][] target) {
        PriorityQueue<Info> pq = new PriorityQueue<>();
        for (int[] ints : target) {
            pq.add(new Info(ints[0], ints[1]));
        }
        return pq;
    }

    public class Info implements Comparable<Info> {
        int s;
        int e;

        public Info(int s, int e) {
            this.s = s;
            this.e = e;
        }

        @Override
        public int compareTo(Info o) {
            if (this.s == o.s) {
                return this.e - o.e;
            }
            return this.s - o.s;
        }
    }
}