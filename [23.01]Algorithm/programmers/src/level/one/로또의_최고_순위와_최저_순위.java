package level.one;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class 로또의_최고_순위와_최저_순위 {
    class Solution {
        public int[] solution(int[] lottos, int[] win_nums) {
            int[] rank = {6,6,5,4,3,2,1};

            long zeroCnt = Arrays.stream(lottos).filter(i->i==0).count();
            Set<Integer> set = Arrays.stream(lottos).boxed().collect(Collectors.toSet());
            long cnt = Arrays.stream(win_nums).filter(i->set.contains(i)).count();
            int[] answer = {rank[(int)(cnt+zeroCnt)], rank[(int)cnt]};

            return answer;
        }
    }
}
