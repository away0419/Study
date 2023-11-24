package level.one;

import java.util.Stack;

public class 크레인_인형뽑기_게임 {
    class Solution {
        public int solution(int[][] board, int[] moves) {
            int answer = 0;
            int size = board.length+1;
            Stack<Integer> stack[] = new Stack[size];

            for(int i=0; i<size; i++){
                stack[i] = new Stack<>();
            }

            for(int i=size-2; i>=0; i--){
                for(int j=0; j<size-1; j++){
                    if(board[i][j] !=0){
                        stack[j+1].add(board[i][j]);
                    }
                }
            }

            for(int i=0; i<moves.length; i++){
                int k = moves[i];

                if(stack[k].isEmpty()){
                    continue;
                }

                int cur = stack[k].pop();

                if(stack[0].isEmpty()){
                    stack[0].push(cur);
                    continue;
                }

                if(cur==stack[0].peek()){
                    answer+=2;
                    stack[0].pop();
                }else{
                    stack[0].push(cur);
                }

            }


            return answer;
        }
    }
}
