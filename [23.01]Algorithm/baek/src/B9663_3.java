import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class B9663_3 {
    static int answer = 0;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        fn(N, 0, arr, 0);
        System.out.println(answer);

    }

    public static void fn(int N, int curX, int[] arr, int cnt) {

        if (cnt == N) {
            answer++;
            return;
        }

        for (int i = 0; i < N; i++) {
            arr[curX] = i;
            if (check(arr, curX)) {
                fn(N, curX + 1, arr, cnt + 1);
            }
        }

    }

    public static boolean check(int[] arr, int curX) {
        for (int i = 0; i < curX; i++) {
            if (arr[i] == arr[curX]) {
                return false;
            } else if (Math.abs(i - curX) == Math.abs((arr[i] - arr[curX]))) {
                return false;
            }
        }

        return true;
    }


}
