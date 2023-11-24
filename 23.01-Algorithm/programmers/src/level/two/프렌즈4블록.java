package level.two;

public class 프렌즈4블록 {
    class Solution {
        static char[][] map;
        public int solution(int m, int n, String[] board) {
            int answer = 0;
            map = new char[m][n];
            for(int i=0; i<m; i++){
                map[i] = board[i].toCharArray();
            }

            int res = search(m, n);
            answer+=res;

            while(res !=0){
                res = search(m,n);
                answer+=res;
            }

            return answer;
        }

        public int search(int m, int n){
            int res =0;
            int[] idx = {0,1,1,0};
            int[] idy = {1,1,0,0};
            boolean visit[][] = new boolean[m][n];

            for(int i=0; i<m-1; i++ ){
                for(int j=0; j<n-1; j++ ){


                    if(map[i][j] =='0'){
                        continue;
                    }

                    int cnt =0;
                    for(int k=0; k<3; k++){
                        int sx = i+idx[k];
                        int sy = j+idy[k];

                        if(map[sx][sy] == map[i][j]){
                            cnt++;
                        }
                    }

                    if(cnt==3){
                        for(int k=0; k<4; k++){
                            int sx = i+idx[k];
                            int sy = j+idy[k];

                            visit[sx][sy] = true;
                        }
                    }
                }
            }

            for(int i=0; i<m; i++ ){
                for(int j=0; j<n; j++ ){
                    if(visit[i][j]){
                        res++;
                        map[i][j] = '0';
                    }
                }
            }


            for(int i=0; i<n; i++ ){
                int k = m-1;
                for(int j=m-1; j>=0; j-- ){
                    if(map[j][i] =='0'){
                        continue;
                    }else{
                        map[k--][i]=map[j][i];
                    }
                }

                for(int j = k; j>=0; j--){
                    map[j][i] ='0';
                }
            }


            return res;
        }



    }
}
