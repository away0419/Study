package level.one;

public class 공원산책 {
    class Solution {
        public int[] solution(String[] park, String[] routes) {
            int[] answer = {};

            for(int i=0; i<park.length; i++){
                for(int j=0; j<park[i].length(); j++){
                    if(park[i].charAt(j)=='S'){
                        answer = new int[]{i,j};
                    }
                }
            }

            for(int i=0; i<routes.length; i++){
                answer = move(answer[0], answer[1], routes[i], park);
            }

            return answer;
        }

        public int[] move(int x, int y, String info, String[] park){
            int vec=0;
            int tx = x;
            int ty = y;
            int[] idx = {-1,1,0,0};
            int[] idy = {0,0,-1,1};

            String[] str = info.split(" ");

            if(str[0].equals("E")){
                vec = 3;
            }else if(str[0].equals("S")){
                vec = 1;
            }else if(str[0].equals("W")){
                vec = 2;
            }else if(str[0].equals("N")){
                vec = 0;
            }

            for(int i=0; i<Integer.parseInt(str[1]); i++){
                tx+=idx[vec];
                ty+=idy[vec];

                if(tx<0 || ty<0 || tx>=park.length || ty>=park[0].length() || park[tx].charAt(ty)=='X'){
                    return new int[]{x,y};
                }

            }

            return new int[]{tx,ty};

        }
    }
}
