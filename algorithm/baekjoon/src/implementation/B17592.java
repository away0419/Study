package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/*
과제는 끝나지 않아!
N : 학기의 총 분

 */
public class B17592 {
    static int N;

    public static class Info {
        int score;
        int time;

        public Info(int score, int time) {
            this.score = score;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Info> stack = new Stack<>();
        int answer = 0;

        N = Integer.parseInt(br.readLine());


        for (int i = 0; i < N; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());

            if (s == 0) {

                if (stack.isEmpty()) {
                    continue;
                }

                Info info = stack.peek();
                if (--info.time == 0) {
                    answer += info.score;
                    stack.pop();
                }

            } else {
                stack.add(new Info(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
                Info info = stack.peek();
                if (--info.time == 0) {
                    answer += info.score;
                    stack.pop();
                }
            }
        }

        System.out.println(answer);

    }
}
