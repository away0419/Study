package creational.dynamicFactory;

import creational.enumFactoryMethod.Drink;
import creational.enumFactoryMethod.Food;
import creational.enumFactoryMethod.Hamburger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DynamicFactory {
    // 클래스를 넣을 Map
    private static final Map<String, Class<? extends Food>> registerTypes = new HashMap<>();

    // map에 기본적으로 들어가는 클래스
    static {
        registerTypes.put("Hamburger", Hamburger.class);
        registerTypes.put("Drink", Drink.class);
    }

    // 이후 개발 도중 추가해야 되는 클래스가 생긴 경우 사용
    public static void setRegisterTypes(String type, Class<? extends Food> cls){
        registerTypes.put(type, cls);
    }

    private static Food getFood(String type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 해당 타입의 클래스 가져오기
        Class<?> cls = registerTypes.get(type);

        if(cls == null){
            throw new RuntimeException();
        }

        // 해당 클래스에서 생성자 가져오기
        Constructor<?> foodConstructor = cls.getDeclaredConstructor();

        // Reflection API를 통해 인스턴스 만들고 업캐스팅
        return (Food) foodConstructor.newInstance();
    }

    public static Food createFood(String type){
        Food food = null;

        try {
            food = getFood(type);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | RuntimeException e) {
            System.err.println("해당 음식이 없습니다.");
        }
        return food;
    }

}
