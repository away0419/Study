package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B12892 {
    public static class Info implements Comparable<Info> {
        int P;
        int V;

        public Info(int p, int v) {
            P = p;
            V = v;
        }

        @Override
        public int compareTo(Info o) {
            return this.P - o.P;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        Info[] infos = new Info[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int P = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());
            infos[i] = new Info(P, V);
        }

        Arrays.sort(infos);

        int start = 0;
        long sum = infos[0].V;
        long answer = sum;

        int i = 1;
        while (i < N) {

            while (infos[i].P - infos[start].P >= D) {
                sum -= infos[start].V;
                start++;
            }

            sum += infos[i].V;
            answer = Math.max(answer, sum);
            i++;
        }

        System.out.println(answer);

    }
}
