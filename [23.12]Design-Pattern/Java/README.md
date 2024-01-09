
> ## 싱글톤 (생성)

<details>
  <summary>Lazy Initialization</summary>

- 늦은 초기화.
- private 생성자 static 메소드를 사용한 가장 보편적인 방식.
- 멀티 스레드 환경에 취약함.
  - 이를 해결 하고자 synchronized 사용.
  - 동기화로 인한 성능 저하 발생.

  ```java
  public class creational.singletone.LazyInitialization {
  
      private static creational.singletone.LazyInitialization instance;
  
      private creational.singletone.LazyInitialization() {
      }
  
      // 동기화 문제 해결을 위한 synchronized
      public static synchronized creational.singletone.LazyInitialization getInstance() {
          if (instance == null) {
              instance = new creational.singletone.LazyInitialization();
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
  public class creational.singletone.EagerInaitialization {
      private static creational.singletone.EagerInaitialization instance = new creational.singletone.EagerInaitialization();
  
      private creational.singletone.EagerInaitialization() {
      }
  
      public static creational.singletone.EagerInaitialization getInstance() {
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
  public class creational.singletone.DoubleCheckedLocking {
      private volatile static creational.singletone.DoubleCheckedLocking instance;
  
      private creational.singletone.DoubleCheckedLocking(){}
  
      public static creational.singletone.DoubleCheckedLocking getInstance(){
          if (instance == null){
              synchronized (creational.singletone.DoubleCheckedLocking.class){
                  if(instance==null){
                      instance = new creational.singletone.DoubleCheckedLocking();
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
  public class creational.singletone.LazyHolder {
  
      private static class LazyHolderInner {
          private final static creational.singletone.LazyHolder INSTANCE = new creational.singletone.LazyHolder();
      }
  
      public static creational.singletone.LazyHolder getInstance() {
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
  package creational.factoryMethod;
  
  public class Drink {
  }
  ```

  ```java
  package creational.factoryMethod;
  
  public class Coffee extends Drink{
  }
  ```

  ```java
  package creational.factoryMethod;
  
  public class Tea extends Drink{
  }
  ```
</details>

<details>
  <summary>Factory 추상화</summary>

- 객체 생성 메소드만 가진 [인터페이스, 추상 클래스] 생성.

  ```java
  package creational.factoryMethod;
  
  public interface DrinkFactory {
      public Drink makeDrink();
  }
  ```

</details>

<details>
  <summary>Factory 구현화</summary>

- 부모를 상속 받은 서브 클래스 생성 또는 바로 기본 클래스 생성.

  ```java
  package creational.factoryMethod;
  
  public class DrinkFactoryImpl implements  DrinkFactory{
      @Override
      public Drink makeDrink() {
          return new Drink();
      }
  }
  ```

  ```java
  package creational.factoryMethod;
  
  public class CoffeeFactoryImpl implements DrinkFactory{
      @Override
      public Drink makeDrink() {
          System.out.println("makeCoffee");
          return new Coffee();
      }
  }
  ```

  ```java
  package creational.factoryMethod;
  
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
  package creational.abstractFactory;
  
  public class BurgerKingHamburger implements Hamburger{
      public BurgerKingHamburger(){
          System.out.println("make BurgerKingHamburger");
      }
  }
  ```

  ```java
  package creational.abstractFactory;
  
  public class BurgerKingDrink implements Drink{
      public BurgerKingDrink(){
          System.out.println("make BurgerKingDrink");
      }
  }
  ```

  ```java
  package creational.abstractFactory;
  
  public class MacdonaldHamburger implements Hamburger{
      public MacdonaldHamburger(){
          System.out.println("make MacdonaldHamburger");
      }
  }
  ```

  ```java
  package creational.abstractFactory;
  
  public class MacdonaldDrink implements  Drink{
      public MacdonaldDrink(){
          System.out.println("make MacdonaldDrink");
      }
  }
  ```

  ```java
  package creational.abstractFactory;
  
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
  package creational.abstractFactory;
  
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
  package creational.abstractFactory;
  
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
  package creational.staticFactoryMethod;
  
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
  package creational.enumFactoryMethod;
  
  public interface Food {
  }
  ```
  ```java
  package creational.enumFactoryMethod;
  
  public class Drink implements Food{
      public Drink(){
          System.out.println("make Drink");
      }
  }
  ```
  ```java
  package creational.enumFactoryMethod;
  
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
  package creational.enumFactoryMethod;
  
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
package creational.builder;

public class Drink {
  private String name;
  private String size;
  private String price;

// 해당 로직은 setter와 다를바 없으며 불변성을 보장하지 못함. creational.builder 패턴이라 보기 힘듬.
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
package creational.builder;

public class DrinkBuilder {
  private String name;
  private String size;
  private String price;

  public DrinkBuilder name(String name) {
    this.name = name;
    return this;
  }

  public DrinkBuilder size(String size) {
    this.size = size;
    return this;
  }

  public DrinkBuilder price(String price) {
    this.price = price;
    return this;
  }

  public Drink build() {
    return new Drink(this.name, this.size, this.price);
  }
}
```

</details>

<details>
  <summary>Inner Class Builder</summary>

- 객체의 생성자를 private 하게 만들 수 있음.

  ```java
  package creational.builder;
  
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

> ## 프로토타입 (생성)

<details>
  <summary>객체</summary>

- Cloneable 상속 받아 오버라이딩.
- 깊은 복사.

  ```java
  package creational.prototpye;
  
  import java.util.ArrayList;
  import java.util.List;
  
  public class Drink implements Cloneable {
      private List<Integer> list = new ArrayList<>();
  
      public Drink(List<Integer> list) {
          this.list = list;
      }
  
      @Override
      protected Object clone() throws CloneNotSupportedException {
          List<Integer> copyList = new ArrayList<>(list);
          return new Drink(copyList);
      }
  
      @Override
      public String toString() {
  
          return "Drink{" +
                  "list = " + System.identityHashCode(list) + list +
                  '}';
      }
  }
  ```
</details>

<br/>
<br/>

> ## 어댑터 (구조)

<details>
  <summary>객체</summary>

- 시동 on/off 기능이 있는 자동차 클래스.
- fly 기능이 있는 날개 인터페이스.

  ```java
  package structural;
  
  public class Car {
  
      public Car(){
          System.out.println("make Car");
      }
  
      public void start(){
          System.out.println("시동 걸기");
      }
  
      public void end(){
          System.out.println("시동 끄기");
      }
  }
  ```
  ```java
  package structural.adaptor;
  
  public interface Wing {
      public void fly();
  }
  ```

</details>

<details>
  <summary>합성</summary>

- 멤버 변수로 기존 클래스를 가짐.
- 추가 기능 인터페이스 상속받음.

  ```java
  package structural.adaptor;
  
  import structural.Car;
  
  public class FlyCar1 implements Wing{
      private Car car;
  
      public FlyCar1(Car car){
          this.car = car;
          System.out.println("make FlyCar1");
      }
  
      public void start(){
          car.start();
      }
  
      public void end(){
          car.end();
      }
  
      @Override
      public void fly() {
          System.out.println("날기");
      }
  }
  ```

</details>

<details>
  <summary>상속</summary>

- 기존 클래스를 상속 받음.
- 추가 기능 인터페이스를 상속 받음.

  ```java
  package structural.adaptor;
  
  import structural.Car;
  
  public class FlyCar2 extends Car implements Wing {
  
      public FlyCar2(){
          System.out.println("make FlyCar2");
      }
  
      @Override
      public void fly() {
          System.out.println("날기");
      }
  }
  ```
</details>

<br/>
<br/>

> ## 브릿지 (구조)

<details>
  <summary>색</summary>

- 색은 버튼의 특징중 하나.
- 버튼이 Color 인터페이스를 바로 상속 받아도 되며 일반적으로 상속을 추천함.
- 해당 예시는 상속이 아닌 사용을 이용한 방법을 이용함.
- interface가 브릿지 역할.

  ```java
  package structural.bridge;
  
  public interface Color {
      public void getColor();
  }
  
  ```
  ```java
  package structural.bridge;
  
  public class Red implements Color{
      @Override
      public void getColor() {
          System.out.println("Red");
      }
  }
  
  ```
  ```java
  package structural.bridge;
  
  public class Blue implements Color{
      @Override
      public void getColor() {
          System.out.println("Blue");
      }
  }
  
  ```
</details>

<details>
  <summary>버튼</summary>

- 버튼을 종류에 따라 객체로 만들 수 있음.
- 만약, 기능별 인터페이스를 따로 구현한다면 아래 예시처럼 Start, End 객체를 각각 만들 필요가 없다.
- 여러 상황을 보여주고자 abstract class를 사용했으며 이를 상속 받는 예시임.
- 즉, 기능은 상속을 이용하였고 특징은 사용을 이용하였다 볼 수 있음.
- abstract class가 브릿지 역할.

  ```java
  package structural.bridge;
  
  public abstract class Button {
      Color color;
  
      protected Button(Color color){
          this.color = color;
      }
  
      public abstract void action();
  }
  ```
  ```java
  package structural.bridge;
  
  public class StartButton extends Button{
  
      public StartButton(Color color) {
          super(color);
      }
  
      @Override
      public void action() {
          System.out.println("Start!!!");
      }
  }
  ```
  ```java
  package structural.bridge;
  
  public class EndButton extends Button{
      public EndButton(Color color) {
          super(color);
      }
  
      @Override
      public void action() {
          System.out.println("End!!!");
      }
  }
  ```

</details>

<br/>
<br/>

> ## 컴포지트 (구조)

<details>
  <summary>인터페이스</summary>

- 공통적인 부분을 추상화.
- Item이 최상위 공통 부분이며, Box는 상위 공통 부분임.

  ```java
  package structural.composite;
  
  public interface Item {
      int getPrice();
      String getName();
  }
  ```
  
  ```java
  package structural.composite;
  
  public interface Box  extends Item{
      void addItem(Item item);
      void removeItem(Item item);
      int getAllPrice();
      String getItems();
  }
  ```
</details>

<details>
  <summary>객체</summary>

- 상자 안에 상자 혹은 아이템이 들어갈 수 있음.
  - List는 최상위 인터페이스 Item을 받을 수 있게 만들었음.

  ```java
  package structural.composite;
  
  public class NormalItem implements Item{
      private String name;
      private int price;
  
      public NormalItem(String name, int price) {
          this.name = name;
          this.price = price;
      }
  
      @Override
      public int getPrice() {
          return this.price;
      }
  
      @Override
      public String getName() {
          return this.name;
      }
  }
  ```

  ```java
  package structural.composite;
  
  import java.util.ArrayList;
  import java.util.List;
  import java.util.stream.Collectors;
  
  public class NormalBox implements Box {
      private final List<Item> list;
      private String name;
      private int price;
  
      public NormalBox(String name, int price) {
          this.name = name;
          this.price = price;
          this.list = new ArrayList<>();
      }
  
      @Override
      public void addItem(Item item) {
          list.add(item);
      }
  
      @Override
      public void removeItem(Item item) {
          list.remove(item);
      }
  
      @Override
      public int getAllPrice() {
          return list.stream()
                  .mapToInt(item -> item instanceof Box box ? box.getAllPrice() + item.getPrice() : item.getPrice())
                  .sum();
      }
  
      @Override
      public int getPrice() {
          return this.price;
      }
  
      @Override
      public String getName() {
          return this.name;
      }
  
      @Override
      public String getItems() {
          return getName() + " = { " + list.stream().map(item -> item instanceof Box box ? box.getItems() : item.getName()).collect(Collectors.joining(", ")) + " }";
      }
  }
  ```

</details>

<br/>
<br/>

> ## 데코레이터 (구조)

<details>
  <summary>인터페이스</summary>

- 햄버거가 가지는 기본 기능을 추상화.

  ```java
  package structural.decorator;
  
  public interface Hamburger {
      public String getName();
  }
  ```

</details>

<details>
  <summary>객체</summary>

- 기본 햄버거 객체.

  ```java
  package structural.decorator;
  
  public class BasicHamBurger implements Hamburger{
      @Override
      public String getName() {
          return "햄버거";
      }
  }
  
  ```

</details>

<details>
  <summary>데코레이터</summary>

- 토핑. 즉, 데코레이터 하려는 특징 또는 기능임. 
- 해당 패턴을 통해 기존 객체에 기능 또는 특징을 더해 새로운 객체로 반환한다.
- 해당 코드는 완전 새로운 객체가 됨. 기존 객체를 사용할 수는 없을 듯 하다.
- 결국, 클래스를 만들어야 하는건 동일하나, 종류별로 모두 만들 필요는 없다.
  - ex) 불고기 불고기 햄버거, 불고기 치즈 햄버거 등 객체 클래스는 불필요.
- 굳이 추상 클래스로 만들 필요는 없을 것 같기도 하다.

  ```java
  package structural.decorator;
  
  public abstract class HamburgerDecorator implements Hamburger{
      private Hamburger hamburger;
  
      public HamburgerDecorator(Hamburger hamburger) {
          this.hamburger = hamburger;
      }
  
      @Override
      public String getName() {
          return hamburger.getName();
      }
  }
  
  ```

  ```java
  package structural.decorator;
  
  public class CheeseDecorator extends HamburgerDecorator{
      public CheeseDecorator(Hamburger hamburger) {
          super(hamburger);
      }
  
      @Override
      public String getName() {
          return "치즈 " + super.getName();
      }
  }
  
  ```

  ```java
  package structural.decorator;
  
  public class BulgogiDecorator extends HamburgerDecorator{
      public BulgogiDecorator(Hamburger hamburger) {
          super(hamburger);
      }
  
      @Override
      public String getName() {
          return "불고기 " + super.getName();
      }
  }
  
  ```

</details>

<br/>
<br/>

> ## 퍼사드 (구조)

<details>
  <summary>객체</summary>

- 필요한 객체들. (사람, 피자, TV)
- 각 객체 별 기능이 있음.

  ```java
  package structural.facade;
  
  public class Person {
      public void move(){
          System.out.println("움직인다");
      }
  
      public void watch(){
          System.out.println("본다");
      }
  }
  ```

  ```java
  package structural.facade;
  
  public class Pizza {
      public void addTopping(){
          System.out.println("토핑 추가");
      }
  
  }
  ```
  
  ```java
  package structural.facade;
  
  public class Tv {
      public void ON(){
          System.out.println("전원 ON");
      }
  }
  ```

</details>

<details>
  <summary>퍼사드</summary>

- 하나의 기능을 위해 필요한 서브 클래스의 기능을 가져와 구현함.
- 따로 자신만의 기능을 구현하지는 않고 서브 클래스의 기능을 호출하는 용도.

  ```java
  package structural.facade;
  
  public class Facade {
      public void action(){
          Person person = new Person();
          Tv tv = new Tv();
          Pizza pizza = new Pizza();
  
          person.move();
          pizza.addTopping();
          person.move();
          tv.ON();
          person.watch();
      }
  }
  
  ```

</details>

<br/>
<br/>

> ## 플라이웨이트 (구조)

<details>
  <summary>객체</summary>

- 먼저 불변인 공통 부분을 따로 빼서 클래스로 만듬. (Model)
- Model의 특성이 동일한지 아닌지 판단하기 위해 Factory에서 고유 키값 부여. (Map 변수 이용, FlyWeightFactory 라고도 불림)
- 해당 Model이 있으면 불러오고 없으면 새로 만듬. 이후 만들어진 Model을 실제 객체의 공통 변수에 넣어줌. (Tree)

  ```java
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
  ```

  ```java
  package structural.flyweight;
  
  public class Tree {
      Model model;
      double x;
      double y;
  
      private Tree(Model model, double x, double y) {
          this.model = model;
          this.x = x;
          this.y = y;
      }
  
      public static class Factory {
          public static Tree getInstance(String type) {
              Model model = Model.Factory.getInstance(type);
              double x = Math.random() * 10000;
              double y = Math.random() * 10000;
  
              System.out.println(type + "의 좌표: x=" + x + ", y=" + y);
              return new Tree(model, x, y);
          }
      }
  }
  ```

</details>

<br/>
<br/>


> ## 프록시 (구조&행동)

<details>
  <summary>가상 프록시</summary>

- 프록시 객체와 실제 객체의 인터페이스를 동일하게 둠.
- 프록시에서 객체의 메소드를 호출하도록 설계.
- 실제 객체가 생성되지 않았음에도 프록시 객체를 통해 로직 넘어감.

  ```java
  package structural.proxy;
  
  interface Image {
      public void showImage();
  
  }
  ```

  ```java
  package structural.proxy;
  
  public class HighImage implements Image{
      String path;
  
      public HighImage(String path) {
          System.out.println(path + " 경로의 이미지 로딩");
          this.path = path;
      }
  
  
      @Override
      public void showImage() {
          System.out.println(path+ " 경로의 이미지 출력");
      }
  }
  
  ```

  ```java
  package structural.proxy;
  
  public class VirtualProxy implements Image {
      String path;
  
  
      public VirtualProxy(String path) {
          this.path = path;
          System.out.println(path +" 경로의 프록시 생성");
      }
  
      @Override
      public void showImage() {
          HighImage highImage = new HighImage(this.path);
          highImage.showImage();
      }
  }
  ```

</details>

<details>
  <summary>보호 프록시</summary>

- 가상 프록시에 권한을 추가한 것.
- 가상 프록시랑 별 차이가 없다.

  ```java
  package structural.proxy;
  
  public class ProtectiveProxy implements Image{
      String path;
      String authority;
  
      public ProtectiveProxy(String path, String authority) {
          this.path = path;
          this.authority = authority;
          System.out.println("["+path +" 경로, "+authority+"사용자] 프록시 생성");
      }
  
      @Override
      public void showImage() {
          if(this.authority.equals("관리자")){
              System.out.println("관리자 접근");
              HighImage highImage = new HighImage(this.path);
              highImage.showImage();
          }else {
              System.out.println(this.authority + "는 접근할 수 없습니다.");
          }
      }
  }
  ```
</details>


<br/>
<br/>

> ## 책임 연쇄

<details>
  <summary>인터페이스</summary>

- Handler가 가지는 기본적인 기능을 포함하고 있음.
- 책임질 다음 Handler Setter와 해당 프로세스에서 진행할 기능 구현을 강제해야 함.

```java
package behavioral.chainOfResponsibility;

public interface Handler {
    void setNextHandler(Handler handler);
    void process(String authority);
}
```

</details>


<details>
  <summary>추상 클래스</summary>

- 해당 추상 클래스는 굳이 없어도 됨.
- 바로 객체에 인터페이스를 상속 받도록 하는게 일반적.
- 해당 예시는 기능적으로 좀더 세분화 해보고자 작성함.

  ```java
  package behavioral.chainOfResponsibility;
  
  public abstract class LoginHandler implements Handler{
  
      Handler handler;
  
      @Override
      public void setNextHandler(Handler handler) {
          this.handler = handler;
      }
  
      @Override
      public void process(String authority) {
          try{
              this.handler.process(authority);
          }catch (Exception e){
              System.out.println("로그인 실패");
          }
      }
  }
  
  ```

</details>


<details>
  <summary>객체</summary>

- 각자의 객체가 process를 자신 만의 기능을 넣어 구현해야함.

```java
package behavioral.chainOfResponsibility;

public class Admin extends LoginHandler {

    @Override
    public void process(String authority) {
        if ("Admin".equals(authority)) {
            System.out.println("관리자 로그인 완료");
        } else {
            super.process(authority);
        }
    }
}
```

```java
package behavioral.chainOfResponsibility;

public class User extends LoginHandler{
    @Override
    public void process(String authority) {
        if("User".equals(authority)){
            System.out.println("사용자 로그인 완료");
        }else {
            super.process(authority);
        }
    }
}
```

</details>

<br/>
<br/>

> ## 커맨드 (행동)

<details>
  <summary>인터페이스</summary>

- 책임 연쇄와 비슷함.
- 기본 기능을 추상화.

  ```java
  package behavioral.command;
  
  public interface Command {
      void run();
  }
  ```

</details>

<details>
  <summary>객체</summary>

- command를 상속받은 객체와 이를 매개 변수로 받을 수 있는 객체.

  ```java
  package behavioral.command;
  
  public class HeaterCommand implements Command{
      @Override
      public void run() {
          System.out.println("히터 ON");
      }
  }
  ```

  ```java
  package behavioral.command;
  
  public class LampCommand implements Command{
      @Override
      public void run() {
          System.out.println("램프 ON");
      }
  }
  ```
  
  ```java
  package behavioral.command;
  
  public class Button {
      private Command command;
  
      public void setCommand(Command command) {
          this.command = command;
      }
  
      public void action(){
          command.run();
      }
  }
  ```

</details>


> ## 인터프리터 (행동)

<details>
  <summary>인터페이스</summary>

- 예제로 사칙연산 계산기를 만들 예정.
- 패턴 개념은 어렵지 않으나 기능 구현 과정이 어려움.

  ```java
  package behavioral.interpreter;
  
  public interface Expression {
      double interpret();
  }
  ```

</details>

<details>
  <summary>객체</summary>

- 사칙연산자 식에는 크게 두개의 객체가 존재한다 볼 수 있음.
- 하나는 피연산자, 다른 하나는 연산자.
- 연산자는 총 4개만 각각의 객체로 구현함.
- 동일안 인터페이스를 상속 받아 피연산자, 연산자 구분 없이 일단 하나의 stack으로 관리할 수 있음.

  ```java
  package behavioral.interpreter;
  
  public class Number implements Expression{
      private double value;
  
      public Number(double value) {
          this.value = value;
      }
  
      @Override
      public double interpret() {
          return value;
      }
  }
  ```

  ```java
  package behavioral.interpreter;
  
  public class Addition implements Expression{
  
      private Expression leftOperand;
      private Expression rightOperand;
  
  
      public Addition(Expression leftOperand, Expression rightOperand) {
          this.leftOperand = leftOperand;
          this.rightOperand = rightOperand;
      }
  
      @Override
      public double interpret() {
          return leftOperand.interpret() + rightOperand.interpret();
      }
  }
  ```

  ```java
  package behavioral.interpreter;
  
  public class Subtraction implements Expression{
      private Expression leftOperand;
      private Expression rightOperand;
  
      public Subtraction(Expression leftOperand, Expression rightOperand) {
          this.leftOperand = leftOperand;
          this.rightOperand = rightOperand;
      }
  
      @Override
      public double interpret() {
          return leftOperand.interpret() - rightOperand.interpret();
      }
  }
  ```

  ```java
  package behavioral.interpreter;
  
  public class Multiplication implements Expression{
  
      private Expression leftOperand;
      private Expression rightOperand;
  
      public Multiplication(Expression leftOperand, Expression rightOperand) {
          this.leftOperand = leftOperand;
          this.rightOperand = rightOperand;
      }
  
      @Override
      public double interpret() {
          return leftOperand.interpret() * rightOperand.interpret();
      }
  }
  ```

  ```java
  package behavioral.interpreter;
  
  public class Division implements Expression {
      private Expression leftOperand;
      private Expression rightOperand;
  
      public Division(Expression leftOperand, Expression rightOperand) {
          this.leftOperand = leftOperand;
          this.rightOperand = rightOperand;
      }
  
      @Override
      public double interpret() {
          if (rightOperand.interpret() == 0) {
              throw new ArithmeticException("Division by zero");
          }
          return leftOperand.interpret() / rightOperand.interpret();
      }
  }
  ```

</details>

<details>
  <summary>기능 구현</summary>

- 사칙연산자는 패턴과 상관 없이 추가적인 기능 구현이 필요하여 추가하였음.
- 가끔 코딩 테스트에 사칙연산을 구현하는 문제가 나오니 숙지하면 좋을 듯 함.

```java
package behavioral.interpreter;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("사칙연산 표현식을 입력하세요:");
        String userInput = scanner.nextLine();

        Expression expression = buildExpression(userInput);

        try {
            double result = expression.interpret();
            System.out.println("결과: " + result);
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        }
    }

    private static Expression buildExpression(String userInput) {
        String[] tokens = userInput.split(" ");
        Stack<Expression> expressionStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                expressionStack.push(new Number(Double.parseDouble(token)));
            } else if ("+-*/".contains(token)) {
                while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek())) {
                    String topOperator = operatorStack.pop();
                    Expression rightOperand = expressionStack.pop();
                    Expression leftOperand = expressionStack.pop();
                    expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator));
                }
                operatorStack.push(token);
            } else {
                throw new IllegalArgumentException("잘못된 표현식입니다: " + token);
            }
        }

        while (!operatorStack.isEmpty()) {
            String topOperator = operatorStack.pop();
            Expression rightOperand = expressionStack.pop();
            Expression leftOperand = expressionStack.pop();
            expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator));
        }

        if (expressionStack.size() == 1) {
            return expressionStack.pop();
        } else {
            throw new IllegalArgumentException("잘못된 표현식입니다.");
        }
    }

    private static Expression createOperatorExpression(Expression left, Expression right, String operator) {
        return switch (operator) {
            case "+" -> new Addition(left, right);
            case "-" -> new Subtraction(left, right);
            case "*" -> new Multiplication(left, right);
            case "/" -> new Division(left, right);
            default -> throw new IllegalArgumentException("지원되지 않는 연산자입니다: " + operator);
        };
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean hasPrecedence(String op1, String op2) {
        return (!op1.equals("*") && !op1.equals("/")) || (!op2.equals("+") && !op2.equals("-"));
    }

}
```

</details>


> ## 반복자 (행동)

<details>
  <summary>Iterator</summary>

- 저장소에서 넘어온 배열을 실질적으로 접근할 수 있게 해주는 역할.
- 공통 코드를 만들어 재사용.

  ```java
  package behavioral.iterator;
  
  public interface Iterator {
      boolean hasNext();
      Object next();
  }
  ```

  ```java
  package behavioral.iterator;
  
  public class HamburgerIterator implements Iterator{
      Hamburger[] arr;
      private int index = 0;
  
      public HamburgerIterator(Hamburger[] arr) {
          this.arr = arr;
      }
  
      @Override
      public boolean hasNext() {
          return index < arr.length;
      }
  
      @Override
      public Hamburger next() {
          return arr[index++];
      }
  }
  ```

</details>

<details>
  <summary>Collection</summary>

- 여러 객체를 저장하기 위한 저장소.
- 저장소에 저장된 배열을 Iterator에 넘기는 역할.

  ```java
  package behavioral.iterator;
  
  public interface Collection {
      Iterator iterator();
  }
  
  ```
  
  ```java
  package behavioral.iterator;
  
  public class HamburgerCollection implements Collection{
      Hamburger[] arr;
      private int index;
  
      public HamburgerCollection(int size) {
          this.arr = new Hamburger[size];
      }
  
      public void add(Hamburger hamburger){
          if(index<arr.length){
              arr[index++] = hamburger;
          }
      }
  
      @Override
      public Iterator iterator() {
          return new HamburgerIterator(this.arr);
      }
  
  }
  ```


</details>

<details>
  <summary>객체</summary>

- 저장소에 담으려는 객체.

  ```java
  package behavioral.iterator;
  
  public class Hamburger {
      String name;
      int price;
  
      public Hamburger(String name, int price) {
          this.name = name;
          this.price = price;
      }
  
      @Override
      public String toString() {
          return "Hamburger{" +
                  "name='" + name + '\'' +
                  ", price=" + price +
                  '}';
      }
  }
  ```

</details>

<br/>
<br/>

