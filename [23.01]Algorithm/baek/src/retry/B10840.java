package retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class B10840 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str1 = br.readLine();
        String str2 = br.readLine();
        int[] arr = new int[26];
        int[] arr2 = new int[26];
        int answer = 0;
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < str1.length(); i++) {
            int idx = str1.charAt(i) - 'a';
            arr[idx]++;
            arr2[idx]++;
        }

        for (int i = 0; i < 26; i++) {
            if (arr[i] == 0) {
                arr[i]--;
                arr2[i]--;
            }
        }

        for (int i = 0; i < str2.length(); i++) {
            int idx = str2.charAt(i) - 'a';

            if (arr[idx] == -1) {
                q.clear();
                System.arraycopy(arr2, 0, arr, 0, 26);
            } else if (arr[idx] == 0) {
                int cur = q.poll();
                while (cur != idx){
                    arr[cur]++;
                    cur = q.poll();
                }
                q.add(idx);
            } else {
                q.add(idx);
                arr[idx]--;
                answer = Math.max(answer, q.size());
            }
        }

        System.out.println(answer);

    }
}
