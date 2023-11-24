package level.two;

import java.util.PriorityQueue;

public class 거리두기_확인하기 {
    class Solution {
        public class Info implements Comparable<Info>{
            int x;
            int y;
            int leng;

            public Info(int x, int y, int leng){
                this.x =x;
                this.y =y;
                this.leng =leng;
            }

            public int compareTo(Info info){
                return info.leng - this.leng;
            }

        }
        public int[] solution(String[][] places) {
            int[] answer = new int[places.length];

            for(int i=0; i<places.length; i++){
                answer[i] = bfs(places[i]);
            }

            return answer;
        }

        public int bfs(String[] place){
            int n = place.length;
            int m = place[0].length();
            int[] idx = {0,1,0,-1};
            int[] idy = {1,0,-1,0};
            char[][] map = new char[n][m];
            int max = 2;
            PriorityQueue<Info> q = new PriorityQueue<>();
            boolean[][] visit = new boolean[n][m];

            for(int i=0; i<n; i++){
                for(int j=0; j<m; j++){
                    map[i][j] = place[i].charAt(j);
                    if(map[i][j] =='P'){
                        q.add(new Info(i,j,0));
                    }
                }
            }

            while(!q.isEmpty()){
                Info info = q.poll();


                if(info.leng == 2){
                    continue;
                }

                visit[info.x][info.y] = true;

                for(int i=0; i<4; i++){
                    int sx = info.x+idx[i];
                    int sy = info.y+idy[i];

                    if(sx<0 || sy<0 || sx>=n || sy>=m || map[sx][sy] =='X' || visit[sx][sy]){
                        continue;
                    }

                    if(map[sx][sy] == 'P'){
                        return 0;
                    }

                    q.add(new Info(sx,sy, info.leng+1));
                }
            }

            return 1;

        }

    }
}
