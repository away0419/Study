package level.two;

import java.util.ArrayDeque;
import java.util.Queue;

public class 카카오프렌즈_컬러링북 {
    class Solution {

        public class Info{
            int x;
            int y;
            int color;

            public Info(int x, int y, int color){
                this.x = x;
                this.y = y;
                this.color =color;
            }
        }

        public int[] solution(int m, int n, int[][] picture) {
            int[] answer = new int[2];
            boolean[][] visit = new boolean[m][n];

            for(int i=0; i<m; i++){
                for(int j=0; j<n; j++){
                    if(!visit[i][j] && picture[i][j] != 0){
                        bfs(i, j, n, m, picture, answer, visit);
                    }
                }
            }


            return answer;
        }

        public void bfs(int x, int y, int n, int m, int[][] picture, int[] answer, boolean[][] visit){
            Queue<Info> q = new ArrayDeque<>();
            int[] idx = {0,0,1,-1};
            int[] idy = {-1,1,0,0};
            int cnt = 0;

            q.add(new Info(x, y, picture[x][y]));
            visit[x][y] = true;

            while(!q.isEmpty()){
                Info info = q.poll();
                cnt++;

                for(int i=0; i<4; i++){
                    int sx = idx[i] + info.x;
                    int sy = idy[i] + info.y;

                    if(sx<0 || sy<0 || sx>=m || sy>=n || visit[sx][sy] || picture[sx][sy] != info.color){
                        continue;
                    }

                    visit[sx][sy] = true;
                    q.add(new Info(sx, sy, picture[sx][sy]));
                }
            }

            answer[0]++;
            answer[1] = Math.max(answer[1], cnt);
        }

    }
}
