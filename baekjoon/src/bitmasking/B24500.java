package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
blobblush
 */
public class B24500 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long N = Long.parseLong(br.readLine());
        long bit = fn(N);
        if(bit!=0){
            System.out.println(2);
            System.out.println(bit);
        }else{
            System.out.println(1);
        }
        System.out.println(N);

    }

    public static long fn(long N){
        long leng = Long.toBinaryString(N).length();
        long bit= 0L;
        for (long i = 0; i < leng; i++) {
            bit|=1L<<i;
        }
        return bit^N;
    }

}
