package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B22866 {
    public static class Info implements Comparable<Info> {
        int no;
        int height;

        public Info(int no, int height) {
            this.no = no;
            this.height = height;
        }

        @Override
        public int compareTo(Info o) {
            if (this.height == o.height) {
                return this.no - o.no;
            }
            return this.height - o.height;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n + 1];
        int[] rcArr = new int[n + 1];
        int[] lcArr = new int[n + 1];
        List<Info>[] list = new ArrayList[n + 1];
        Stack<Info> lstack = new Stack<>();
        Stack<Info> rstack = new Stack<>();
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            list[i] = new ArrayList<>();
        }


        for (int i = n; i >= 1; i--) {
            while (!rstack.isEmpty() && rstack.peek().height <= arr[i]) {
                rstack.pop();
            }

            if (!rstack.isEmpty()) {
                list[i].add(new Info(rstack.peek().no, Math.abs(rstack.peek().no-i)));
            }

            rcArr[i] = rstack.size();
            rstack.push(new Info(i, arr[i]));

            while (!lstack.isEmpty() && lstack.peek().height <= arr[n + 1 - i]) {
                lstack.pop();
            }

            if (!lstack.isEmpty()) {
                list[n + 1 - i].add(new Info(lstack.peek().no, Math.abs(lstack.peek().no-(n+1-i))));
            }

            lcArr[n + 1 - i] = lstack.size();
            lstack.push(new Info(n + 1 - i, arr[n + 1 - i]));
        }


        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= n; i++) {
            sb.append(rcArr[i] + lcArr[i]);
            if (!list[i].isEmpty()) {
                Collections.sort(list[i]);
                sb.append(" ").append(list[i].get(0).no);
            }
            sb.append("\n");
        }

        System.out.println(sb);

    }
}
