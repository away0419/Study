package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
숫자 야구
set<Integer> : 현재 조건에서 가능한 수 중 answer에 있는 수
 */
public class B2503 {
    static int N;
    static Info[] infos;

    public static class Info {
        String number;
        int strikeCnt;
        int ballCnt;

        public Info(String number, int strikeCnt, int ballCnt) {
            this.number = number;
            this.strikeCnt = strikeCnt;
            this.ballCnt = ballCnt;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        infos = new Info[N];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            infos[i] = new Info(st.nextToken(), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        System.out.println(play());


    }

    public static int play() {
        int res = 0;
        for (int i = 123; i <= 987; i++) {

            if (checkNumber(i)) {
                continue;
            }
            
            int passCnt = 0;
            for (Info info :
                    infos) {
                String playNumber = info.number;
                String answer = String.valueOf(i);
                int strikeCnt = strikeCheck(playNumber, answer);
                int ballCnt = ballCheck(playNumber, answer);

                if (strikeCnt == info.strikeCnt && ballCnt == info.ballCnt) {
                    passCnt++;
                } else {
                    break;
                }
            }

            if (passCnt == N) {
                res++;
            }
        }
        return res;
    }

    public static int strikeCheck(String playNumber, String answer) {
        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            if (playNumber.charAt(i) == answer.charAt(i)) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int ballCheck(String playNumber, String answer) {
        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            if (playNumber.charAt(i) == answer.charAt((i + 1) % 3)
                    || playNumber.charAt(i) == answer.charAt((i + 2) % 3)) {
                cnt++;
            }
        }
        return cnt;
    }


    public static boolean checkNumber(int number) {
        String sNum = String.valueOf(number);
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            set.add(sNum.charAt(i));
        }
        return set.contains('0') || set.size() != 3;
    }


}
