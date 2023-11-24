package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B12107 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        if(N ==1){
            System.out.println("B");
        }else{
            System.out.println("A");
        }

    }

}
