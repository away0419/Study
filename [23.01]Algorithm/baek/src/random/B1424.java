package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1424 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 6
        int L = Integer.parseInt(br.readLine()); // 2
        int C = Integer.parseInt(br.readLine()); // 3


        int length = L + 1;
        int volume = C + 1;

        int cnt = volume / length; // 1

        int answer = 1000000;

        for (int i = 1; i <= cnt; i++) { // i 는 cd 한장에 들어갈 곡의 갯수
            if (i % 13 == 0) { // i는 13 배수일 수 없다.
                continue;
            }

            int cdCnt = N / i; // cdCnt는 i일 때 필요한 최소 cd 갯수
            int k = N % i; // k는 cdCnt 만큼 i곡을 넣고 남은 자투리 노래 갯수
            int s = 0; // s는 추가 cd 갯수

            if (k != 0) { // 자투리 노래가 남은 경우
                s = 1; // 최소 1장의 cd가 필요함.
                if (k % 13 == 0 && (cdCnt == 0 || k + 1 == i)) { // 만약 자투리 노래 갯수가 13배수 일때
                    // 최소 cd 갯수가 0이어서 다른 cd에 들어있는 하나의 곡을 가져오지 못하거나
                    // 다른 cd에 들어있는 하나의 곡을 가져와서 자투리 노래 갯수에 더한 결과 값이 i라면
                    // 자투리 노래를 하나의 cd에 담지 못하므로 s는 2가 된다.
                    s = 2;
                }
            }
            answer = Math.min(answer, cdCnt + s);
        }

        System.out.println(answer);
    }
}
