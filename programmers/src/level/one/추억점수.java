package level.one;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class 추억점수 {
    public int[] solution(String[] name, int[] yearning, String[][] photo) {
        int[] answer = {};
        Map<String, Integer> map = IntStream.range(0, name.length)
                .boxed()
                .collect(Collectors.toMap(i -> name[i], i -> yearning[i]));

        for(int i=0; i<photo.length; i++){
            int sum =0;
            for(int j=0; j<photo[i].length; j++){
                sum+= map.getOrDefault(photo[i][j],0);
            }
            answer[i] = sum;
        }

        return answer;
    }
}
