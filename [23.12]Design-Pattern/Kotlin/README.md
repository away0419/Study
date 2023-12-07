> ## 싱글톤 패턴

<details>
    <summary>object</summary>

- java의 static과 kotlin의 object은 동일하게 보이지만 다름.
  - static은 클래스 로더가 클래스를 읽을 때 안에 static이 있다면 메모리 영역에 적재하는 것 뿐임. 새로운 객체를 생성할 수 있음.
  - object는 인스턴스 객체를 단 1개 만들어줌. 새로운 객체를 생성할 수 없음. 이때 만들어진 객체명은 클래스명과 동일함. 
- 실제 사용될 때 초기화 됨.
- 생성자가 없는 클래스만 사용 가능. 
  - 생성자가 없으므로 파라미터를 전달하려면 set으로 설정하는 수밖에 없음.
- 내부 변수가 여러개 일 때, 하나의 변수에 접근만 해도 나머지 하나의 변수도 초기화 된다.
  - 이를 막고 싶다면 변수에 by lazy 사용. (특이한 경우가 아니라면 사용 안할 듯함.)
- 클래스명.(함수/필드)로 호출 가능.
- 기본적으로 스레드 안전.

  ```kotlin
  package singleton
    
  object ObjectSingleton {
    val firstValue = "first"
    val secoundValue by lazy {"lazy"}
  }
  ```

</details>

<details>
    <summary>companion object</summary>

- 해당 클래스가 로드될 때 초기화 됨.
- companion object가 적용된 내부만 싱글톤 객체가 됨. 즉 외부 클래스는 싱글톤 아님.
  - 클래스 수준의 정적 멤버가 필요할 때 사용할 수 있음.
  - 생성자를 만들 수 있어 파라미터를 전달할 수 있음.
- companion object 내에 선언된 속성과 함수는 클래스명/객체명.(함수/필드) 호출 가능.

  ```kotlin
  package singleton
  
  class CompanionObjectSingletone private constructor() {
  
  //    Lazy Initialization
  //    companion object {
  //        private var instance: CompanionObjectSingletone? = null;
  //
  //        fun getInstance(): CompanionObjectSingletone {
  //            return instance ?: CompanionObjectSingletone().also {
  //                    instance = it
  //            }
  //        }
  //    }
  
  //    Eager Initialization
  //    companion object {
  //        private var instance: CompanionObjectSingletone =  CompanionObjectSingletone();
  //
  //        fun getInstance(): CompanionObjectSingletone {
  //            return instance
  //        }
  //    }
  
  //    double checked locking
  //    companion object {
  //        @Volatile private var instance: CompanionObjectSingletone? = null;
  //
  //        fun getInstance(): CompanionObjectSingletone {
  //            return instance ?: synchronized(this) {
  //                instance ?: CompanionObjectSingletone().also {
  //                    instance = it
  //                }
  //            }
  //        }
  //    }
  
  //    Lazy Holder
  //    inner 키워드를 사용하지 않으면 static 내부 클래스(Inner 클래스) 로 되고
  //    inner 키워드를 사용해야 non-static 내부 클래스(Nested 클래스) 가 된다.
  //    private class LazyHolderInner{
  //        companion object{
  //            val companionObjectSingletone : CompanionObjectSingletone = CompanionObjectSingletone()
  //        }
  //    }
  //
  //    companion object {
  //        fun getInstance(): CompanionObjectSingletone {
  //            return LazyHolderInner.companionObjectSingletone
  //        }
  //    }
  
      // kotlin singleton 완벽한 방법. 
  //    lazy 이용하여 스레드 안전 보장.
  //    생성자도 만들 수 있어 파라미터도 받을 수 있음.
      companion object {
          private val instance: CompanionObjectSingletone by lazy { CompanionObjectSingletone() }
  
          fun getInstance(): CompanionObjectSingletone {
              return instance
          }
      }
  
  }
  ```
</details>


> ## 팩토리 메소드

<details>
  <summary>객체</summary>

- 생성 하려는 객체들

```kotlin
package factoryMethod

open class Drink {
}
```

```kotlin
package factoryMethod

class Coffee : Drink(){
}
```

```kotlin
package factoryMethod

class Tea : Drink() {
}
```
</details>

<details>
  <summary>factory 부모</summary>

- factory 인터페이스
- 객체 생성 메소드를 가지고 있음.

  ```kotlin
  package factoryMethod
  
  fun interface DrinkFactory {
      fun makeDrink() : Drink
  }
  ```
</details>

<details>
  <summary>factory 자식</summary>

- 실제 객체 생성 기능을 구현한 서브 클래스.

  ```kotlin
  package factoryMethod
  
  class DrinkFactoryImpl : DrinkFactory {
      override fun makeDrink(): Drink {
          println("make Drink")
          return Drink()
      }
  }
  ```

  ```kotlin
  package factoryMethod
  
  class CoffeeFactory : DrinkFactory {
      override fun makeDrink(): Drink {
          println("make Coffee")
          return Coffee()
      }
  }
  ```

  ```kotlin
  package factoryMethod
  
  class TeaFactoryImpl : DrinkFactory{
      override fun makeDrink(): Drink {
          println("make Tea")
          return Tea()
      }
  }
  ```
</details>