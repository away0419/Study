package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B15927 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        int end = str.length() - 1;
        char ch = str.charAt(0);
        boolean check = true;
        boolean check2 = true;

        for (int i = 0; i <= end; i++, end--) {
            if (str.charAt(i) != str.charAt(end)) {
                check = false;
                break;
            }else if(str.charAt(i) != ch){
                check2 = false;
            }
        }


        if (!check) {
            System.out.println(str.length());
        } else {
            if (check2) {
                System.out.println(-1);
            } else {
                System.out.println(str.length() - 1);
            }
        }
    }
}
