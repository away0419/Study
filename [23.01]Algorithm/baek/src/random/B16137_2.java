package random;

import javax.management.loading.MLet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class B16137_2 {
    public static class Info {
        int x;
        int y;
        int length;

        public Info(int x, int y, int length) {
            this.x = x;
            this.y = y;
            this.length= length;
        }
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }


    public static void bfs(int N){
        boolean[][] visits = new boolean[N][N];
        Queue<Info> q = new ArrayDeque<>();
        int[] idx = {0,0,0,-1,1};
        int[] idy = {0,-1,1,0,0};

        q.add(new Info(0,0, 0));
        visits[0][0] = true;

        while(!q.isEmpty()){
            Info info = q.poll();

            if(info.x == N-1 && info.y == N-1){
                return;
            }

            for (int i = 0; i < 4; i++) {
                int sx = info.x + idx[i];
                int sy = info.y + idy[i];

                if(sx<0 || sy<0 || sx>=N || sy>=N){
                    continue;
                }





            }


        }


    }


}
