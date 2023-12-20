
> ## 싱글톤 (생성)

<details>
  <summary>Lazy Initialization</summary>

- 늦은 초기화.
- private 생성자 static 메소드를 사용한 가장 보편적인 방식.
- 멀티 스레드 환경에 취약함.
  - 이를 해결 하고자 synchronized 사용.
  - 동기화로 인한 성능 저하 발생.

  ```java
  public class singletone.LazyInitialization {
  
      private static singletone.LazyInitialization instance;
  
      private singletone.LazyInitialization() {
      }
  
      // 동기화 문제 해결을 위한 synchronized
      public static synchronized singletone.LazyInitialization getInstance() {
          if (instance == null) {
              instance = new singletone.LazyInitialization();
          }
  
          return instance;
      }
  }
  ```
</details>

<details>
  <summary>Eager Initialization</summary>

- 이른 초기화.
- 늦은 초기화에서 발생하는 동기화 성능 문제를 해결한 방법.
- static 인스턴스를 미리 생성하여 하나의 인스턴스만 생기도록 보장.
  - 인스턴스를 사용하지 않을 경우 메모리 낭비됨.

  ```java
  public class singletone.EagerInaitialization {
      private static singletone.EagerInaitialization instance = new singletone.EagerInaitialization();
  
      private singletone.EagerInaitialization() {
      }
  
      public static singletone.EagerInaitialization getInstance() {
          return instance;
      }
  }
  ```
</details>

<details>
  <summary>Double Checked Locking</summary>

- volatile 키워드 사용하는 방식.
  - volatile 키워드는 자바 변수를 Main Memory 저장 함.
  - 멀티 스레드 환경에서는 하나의 스레드만 읽기/쓰기 가능 하고 나머지 스레드는 read 가능 하여 최신값 보장.
  - 변수 값을 읽을 때 CPU Cache에 저장된 값이 아닌 Main Memory에서 읽음.
    - 멀티 스레드일 경우 각각의 스레드는 CPU Cache에 저장된 각각의 값을 사용하므로 값의 불일치가 발생함.
- 늦은 초기화와 유사함.
- synchronized 키워드가 메소드 내부에 있음.
  - 메소드를 호출 할 때마다 동기화 걸리지 않아 좀 더 효율적.
  - 인스턴스를 필요로 하는 시점에 만들 수 있음.
- Java 1.5 이상만 가능.

  ```java
  public class singletone.DoubleCheckedLocking {
      private volatile static singletone.DoubleCheckedLocking instance;
  
      private singletone.DoubleCheckedLocking(){}
  
      public static singletone.DoubleCheckedLocking getInstance(){
          if (instance == null){
              synchronized (singletone.DoubleCheckedLocking.class){
                  if(instance==null){
                      instance = new singletone.DoubleCheckedLocking();
                  }
              }
          }
  
          return instance;
      }
  }
  ```
</details>

<details>
  <summary>Lazy Holder</summary>

- 현 시점 가장 완벽한 방법.
- inner class 특징인 호출 되기 전 참조 되지 않는 방식, static 특징인 한번만 호줄 하는 방식, final 키워드를 이용한 불변성 보장 등을 이용함.

  ```java
  public class singletone.LazyHolder {
  
      private static class LazyHolderInner {
          private final static singletone.LazyHolder INSTANCE = new singletone.LazyHolder();
      }
  
      public static singletone.LazyHolder getInstance() {
          return LazyHolderInner.INSTANCE;
      }
  }
  ```
</details>

<br/>
<br/>

> ## 팩토리 메소드 (생성)

<details>
  <summary>객체</summary>

- Drink가 부모, Coffee와 Tea는 자식 클래스.
- 해당 클래스들은 Factory의 부모 클래스는 아님.

  ```java
  package factoryMethod;
  
  public class Drink {
  }
  ```
  
  ```java
  package factoryMethod;
  
  public class Coffee extends Drink{
  }
  ```
  
  ```java
  package factoryMethod;
  
  public class Tea extends Drink{
  }
  ```
</details>

<details>
  <summary>Factory 추상화</summary>

- 객체 생성 메소드만 가진 [인터페이스, 추상 클래스] 생성.

  ```java
  package factoryMethod;
  
  public interface DrinkFactory {
      public Drink makeDrink();
  }
  ```

</details>

<details>
  <summary>Factory 구현화</summary>

- 부모를 상속 받은 서브 클래스 생성 또는 바로 기본 클래스 생성.

  ```java
  package factoryMethod;
  
  public class DrinkFactoryImpl implements  DrinkFactory{
      @Override
      public Drink makeDrink() {
          return new Drink();
      }
  }
  ```
  
  ```java
  package factoryMethod;
  
  public class CoffeeFactoryImpl implements DrinkFactory{
      @Override
      public Drink makeDrink() {
          System.out.println("makeCoffee");
          return new Coffee();
      }
  }
  ```
  
  ```java
  package factoryMethod;
  
  public class TeaFactoryImpl implements DrinkFactory{
      @Override
      public Drink makeDrink() {
          System.out.println("makeTea");
          return new Tea();
      }
  }
  ```
</details>

<br/>
<br/>

> ## 추상 팩토리 (생성)

<details>
  <summary>객체</summary>

- 객체 집합 별 객체를 생성할 것임.
- 버거 세트가 객체 집합임. 매장별 각각 [햄버거, 음료수] 객체가 있음.

  ```java
  package abstractFactory;
  
  public class BurgerKingHamburger implements Hamburger{
      public BurgerKingHamburger(){
          System.out.println("make BurgerKingHamburger");
      }
  }
  ```
  
  ```java
  package abstractFactory;
  
  public class BurgerKingDrink implements Drink{
      public BurgerKingDrink(){
          System.out.println("make BurgerKingDrink");
      }
  }
  ```
  
  ```java
  package abstractFactory;
  
  public class MacdonaldHamburger implements Hamburger{
      public MacdonaldHamburger(){
          System.out.println("make MacdonaldHamburger");
      }
  }
  ```
  
  ```java
  package abstractFactory;
  
  public class MacdonaldDrink implements  Drink{
      public MacdonaldDrink(){
          System.out.println("make MacdonaldDrink");
      }
  }
  ```

  ```java
  package abstractFactory;
  
  public class BurgerSet {
      private final Hamburger hamburger;
      private final Drink drink;
  
      public BurgerSet(Hamburger hamburger, Drink drink) {
          this.hamburger = hamburger;
          this.drink = drink;
      }
  
      public Hamburger getHamburger() {
          return hamburger;
      }
  
      public Drink getDrink() {
          return drink;
      }
  }
  ```


</details>

<details>
  <summary>Factory 추상화</summary>

- 굳이 따진다면 해당 팩토리는 버거 세트의 팩토리 메소드 패턴임.
- 타입 별 객체 집합 군 객체를 만드는 팩토리 이기 때문에 추상 팩토리라 할 수 있음.
- 결국 팩토리 메소드와 추상 팩토리는 서로 관계가 있음. 그렇다고 동일한 패턴은 아님.

  ```java
  package abstractFactory;
  
  public interface BurgerSetFactory {
      public BurgerSet makeSet(String type);
  }
  ```

</details>

<details>
  <summary>Factory 구현화</summary>

- 실제 객체를 생성 하는 로직 구현.
- 타입별 버거 세트를 만들어서 반환함.

  ```java
  package abstractFactory;
  
  public class BurgerSetFactoryImpl implements BurgerSetFactory{
      @Override
      public BurgerSet makeSet(String type) {
          BurgerSet burgerSet = null;
          switch (type){
              case "BurgerKing" -> burgerSet = new BurgerSet(new BurgerKingHamburger(), new BurgerKingDrink());
              case "Macdonald" -> burgerSet = new BurgerSet(new MacdonaldHamburger(), new MacdonaldDrink());
              default -> System.out.println("해당 버거 세트가 없음");
          }
          return burgerSet;
      }
  }
  ```
</details>

<br/>
<br/>

> ## 정적 팩토리 메소드 (생성)

<details>
  <summary>객체</summary>

- 객체 안에 객체를 반환하는 스태틱 메소드가 있음.

  ```java
  package staticFactoryMethod;
  
  public class Drink {
      private Drink(){}
  
      public static Drink from(String msg){
          System.out.println("make Drink" + msg);
          return new Drink();
      }
  
      public static Drink of(String... msg){
          System.out.println("make Drink");
          for (String str :
                  msg) {
              System.out.println(str);
          }
          return new Drink();
      }
  
      public static Drink getInstance(){
          return new Drink();
      }
  
      public static Drink newInstance(){
          return new Drink();
      }
  
      public static String getString(){
          return "Drink";
      }
  
      public static String newString(){
          return "Drink";
      }
  }
  ```
</details>

<br/>
<br/>

> ## 이넘 팩토리 메소드 (생성)
<details>
  <summary>객체</summary>

- 음식을 상속받은 음료수와 햄버거.

  ```java
  package enumFactoryMethod;
  
  public interface Food {
  }
  ```
  ```java
  package enumFactoryMethod;
  
  public class Drink implements Food{
      public Drink(){
          System.out.println("make Drink");
      }
  }
  ```
  ```java
  package enumFactoryMethod;
  
  public class Hamburger implements Food{
      public Hamburger(){
          System.out.println("make Hamburger");
      }
  }
  ```

</details>

<details>
  <summary>Factory Enum</summary>

- Enum 상수로 음료수, 햄버거 생성.
- 추상 메소드를 만들어 모든 상수에서 구현하도록 강제함.

  ```java
  package enumFactoryMethod;
  
  public enum EnumFoodFactory {
      DRINK("음료수"){
        public Food createFood(){
            return new Drink();
        }
      },
      HAMBURGER("햄버거") {
          public Food createFood(){
              return new Hamburger();
          }
      };
  
      private final String name;
  
      EnumFoodFactory(String name) {
          this.name = name;
      }
      String getName(){
          return this.name;
      }
  
      // 추상 메소드. 모든 상수에서 구현 해야 함.
      abstract Food createFood();
  }
  ```
</details>


<br/>
<br/>


> ## 다이나믹 팩토리 (생성)

<details>
  <summary>Factory Dynamic</summary>

- 객체는 Enum Factory에서 사용한 객체 재사용함.
- 예외 처리가 중요함.

  ```java
  package dynamicFactory;
  
  import enumFactoryMethod.Drink;
  import enumFactoryMethod.Food;
  import enumFactoryMethod.Hamburger;
  
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
  ```
</details>


<br/>
<br/>

> ## 빌더 (생성)

<details>
  <summary>객체</summary>

- 음료수.
- 객체 안에 빌더 처럼 메소드를 구현할 순 있으나, 빌더 패턴은 아니고 단순 Setter임.
- 생성자를 private 하게 만들 수 없음.

```java
package builder;

public class Drink {
    private String name;
    private String size;
    private String price;

// 해당 로직은 setter와 다를바 없으며 불변성을 보장하지 못함. builder 패턴이라 보기 힘듬.
//    public Drink name(String name){
//        this.name = name;
//        return this;
//    }
//
//    public Drink size(String size){
//        this.size = size;
//        return this;
//    }
//
//    public Drink price(String price){
//        this.price = price;
//        return this;
//    }

    public Drink(String name, String size, String price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
```

</details>

<details>
  <summary>Builder</summary>

- Drink 생성 역할을 하는 클래스.

```java
package builder;

public class DrinkBuilder {
    private String name;
    private String size;
    private String price;

    public DrinkBuilder name(String name){
        this.name = name;
        return this;
    }

    public DrinkBuilder size(String size){
        this.size = size;
        return this;
    }

    public DrinkBuilder price(String price){
        this.price = price;
        return this;
    }

    public Drink build(){
        return new Drink(this.name, this.size, this.price);
    }
}
```

</details>

<details>
  <summary>Inner Class Builder</summary>

- 객체의 생성자를 private 하게 만들 수 있음.

  ```java
  package builder;
  
  public class Hamburger {
      private String name;
      private String size;
      private int price;
  
      public static class HamburgerBuilder{
          private String name;
          private String size;
          private int price;
  
          public HamburgerBuilder name(String name){
              this.name = name;
              return this;
          }
  
          public HamburgerBuilder size(String size){
              this.size = size;
              return this;
          }
  
          public HamburgerBuilder price(int price){
              this.price = price;
              return this;
          }
  
          public Hamburger build(){
              return new Hamburger(this.name, this.size, this.price);
          }
      }
  
      private Hamburger(String name, String size, int price) {
          this.name = name;
          this.size = size;
          this.price = price;
      }
  
      @Override
      public String toString() {
          return "Hamburger{" +
                  "name='" + name + '\'' +
                  ", size='" + size + '\'' +
                  ", price=" + price +
                  '}';
      }
  }
  ```

</details>

<br/>
<br/>

