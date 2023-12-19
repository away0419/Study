package dynamicFactory

import enumFactoryMethod.Drink
import enumFactoryMethod.Food
import enumFactoryMethod.Hamburger
import java.lang.RuntimeException

object DynamicFactory {

    // out을 통해 하위 객체도 저장될 수 있도록 한다.
    private val registerTypes: MutableMap<String, Class<out Food>> = HashMap();

    // 기본 클래스 타입 저장
    init {
        registerTypes["Hamburger"] = Hamburger::class.java
        registerTypes["Drink"] = Drink::class.java
    }

    // 실행 도중 추가하고 싶어진 경우
    fun setRegisterType(type: String, cls: Class<out Food>){
        registerTypes[type] = cls
    }

    // 클래스 확인 후 리턴
    private fun getFoodClass(type: String): Class<out Food> {
        return registerTypes[type] ?: throw RuntimeException("해당 음식 없음")
    }

    // 클래스 생성자를 이용하여 새로운 객체를 만들고 Food로 형변환
    fun createFood(type: String): Food {
        return getFoodClass(type).getDeclaredConstructor().newInstance() as Food
    }

}