package level3_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B16986 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        boolean visits[] = new boolean[N];
        int[] kyung = new int[20];
        int[] min = new int[20];
        int[][] map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st= new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            kyung[i] = Integer.parseInt(st.nextToken());
            min[i] = Integer.parseInt(st.nextToken());
        }



    }

    public static boolean fn(int N, int K, int[] kyung, int[] min, boolean visits[], int player1, int player2, int[] win, int[] gameCnt){
        if(win[0] == K){
            return true;
        }

        if(win[1] == K || win[2] == K){
            return false;
        }


        for(int i=0; i<N; i++){
            if(visits[i]){
                continue;
            }
            visits[i] = true;

            int winner = player2;





            visits[i] = false;

        }

        return false;
    }
}
