package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
A -> B
 */

public class B16953 {
    static int A, B;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        int cnt = 1;
        while(B>=A){
            if(B==A){
                System.out.println(cnt);
                return;
            }
            cnt++;
            if(B%10 == 1){
                B/=10;
                continue;
            }
            if(B%2 != 0){
                break;
            }
            B/=2;
        }
        System.out.println(-1);
    }
}
