package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B20040 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] parents = new int[N];
        int i = 0;
        boolean check = false;

        for (int j = 0; j < N; j++) {
            parents[j] = j;
        }

        while(i++<M){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(union(a,b,parents)){
                check = true;
                break;
            }

        }

        if(check){
            System.out.println(i);
        }else{
            System.out.println(0);
        }

    }

    public static int find(int x, int[] parents){
        if(parents[x] != x){
            return find(parents[x], parents);
        }

        return parents[x];
    }

    public static boolean union(int a, int b, int[] parents){
        int fa = find(a, parents);
        int fb = find(b, parents);

        if(fa == fb){
            return true;
        }

        if(fa<fb){
            parents[fb] = fa;
        }else{
            parents[fa] = fb;
        }

        return false;
    }


}
