package level.two;

public class 이모티콘_할인행사 {
    class Solution {
        public int[] solution(int[][] users, int[] emoticons) {
            int[] answer = new int[2];
            int n = users.length;
            int m = emoticons.length;
            int[][] sales = new int[m][2];

            permutation(n, m, users, emoticons, 0, sales, answer);


            return answer;
        }

        public void permutation(int n, int m, int[][] users, int[] emoticons, int cnt, int[][] sales, int[] answer){
            if(cnt == m){
                calculation(n, m, users, emoticons, sales, answer);
                return;
            }

            for(int i=1; i<=4; i++){
                sales[cnt][0] = i*10;
                sales[cnt][1] = emoticons[cnt] - (emoticons[cnt]*i/10);
                permutation(n, m, users, emoticons, cnt+1, sales, answer);
            }

        }

        public void calculation(int n, int m, int[][] users, int[] emoticons, int[][] sales, int[] answer){
            int cnt = 0;
            int totalPrice = 0;

            for(int i=0; i<n; i++){
                int sum = 0;

                for(int j=0; j<m; j++){
                    if(sales[j][0] >= users[i][0]){
                        sum += sales[j][1];

                    }
                }

                if(sum >= users[i][1]){
                    cnt++;
                }else{
                    totalPrice += sum;
                }


            }

            if(cnt > answer[0]){
                answer[0] = cnt;
                answer[1] = totalPrice;
            }else if(cnt == answer[0]){
                if(totalPrice>answer[1]){
                    answer[1] = totalPrice;
                }
            }
        }
    }
}
