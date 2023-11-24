package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
잃어버린 괄호
 */
public class B1541 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split("-");

        int answer = 0;
        for (int i = 0; i < str.length; i++) {
            int sum = 0;
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < str[i].length(); j++) {
                if (str[i].charAt(j) == '+') {
                    sum += Integer.parseInt(sb.toString());
                    sb = new StringBuilder();
                } else {
                    sb.append(str[i].charAt(j));
                }
            }
            sum += Integer.parseInt(sb.toString());

            if (i == 0) {
                answer += sum;
            } else {
                answer -= sum;
            }
        }

        System.out.println(answer);

    }
}
