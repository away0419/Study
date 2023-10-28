package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2291 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());


    }

    public static boolean combination(int cur, int cnt, int sum, int N, int M) {
        if(sum>M){
            return false;
        }
        
        if(cnt == N){
            if(sum == M){
                return true;
            }
            return false;
        }

        for (int i = 0; i < ; i++) {
            
        }
        
        return false;
    }

}
