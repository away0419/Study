package structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public class Model {
    String type;

    private Model(String type) {
        this.type = type;
    }

    public static class Factory {
        private static final Map<String, Model> cache = new HashMap<>();

        public static Model getInstance(String type) {
            if (cache.containsKey(type)) {
                System.out.print("[기존 나무 모델 가져오기] ");
                return cache.get(type);
            } else {
                Model model = new Model(type);
                cache.put(type, model);
                System.out.print("[새로운 나무 모델 생성하기] ");
                return model;
            }
        }
    }
}
