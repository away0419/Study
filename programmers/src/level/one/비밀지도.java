package level.one;

public class 비밀지도 {
    class Solution {
        public String[] solution(int n, int[] arr1, int[] arr2) {
            String[] answer = new String[n];

            for(int i=0; i<n; i++){
                int res = arr1[i] | arr2[i];
                StringBuilder sb = new StringBuilder();

                for(int j=0; j<n; j++){
                    if(res%2==0){
                        sb.insert(0," ");
                    }else{
                        sb.insert(0,"#");
                    }
                    res/=2;
                }

                answer[i] = sb.toString();

            }


            return answer;
        }
    }
}
