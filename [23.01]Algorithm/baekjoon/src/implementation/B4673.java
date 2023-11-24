package implementation;

import java.io.IOException;

/*
셀프 넘버
arr[] : 배열

 */
public class B4673 {
    static boolean arr[];

    public static void main(String[] args) throws IOException {
        arr = new boolean[10001];
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10000; i++) {
            if (!arr[i]) {
                sb.append(i).append("\n");
                fn(i);
            }
        }
        System.out.println(sb);
    }

    public static void fn(int a) {
        int sum = a;
        while (sum <= 10000) {
            arr[sum] = true;
            while (a > 0) {
                int k = a % 10;
                sum += k;
                a /= 10;
            }
            a = sum;
        }
    }

}
