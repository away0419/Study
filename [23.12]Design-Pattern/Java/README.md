> ## Lazy Initialization 

- 늦은 초기화.
- private 생성자 static 메소드를 사용한 가장 보편적인 방식.
- 멀티 스레드 환경에 취약함.
  - 이를 해결 하고자 synchronized 사용.
  - 동기화로 인한 성능 저하 발생.

  ```java
  public class LazyInitialization {
  
      private static LazyInitialization instance;
  
      private LazyInitialization() {
      }
  
      // 동기화 문제 해결을 위한 synchronized
      public static synchronized LazyInitialization getInstance() {
          if (instance == null) {
              instance = new LazyInitialization();
          }
  
          return instance;
      }
  }
  ```

<br/>
<br/>


> ## Eager Initialization

- 이른 초기화.
- 늦은 초기화에서 발생하는 동기화 성능 문제를 해결한 방법.
- static 인스턴스를 미리 생성하여 하나의 인스턴스만 생기도록 보장.
  - 인스턴스를 사용하지 않을 경우 메모리 낭비됨.

  ```java
  public class EagerInaitialization {
      private static EagerInaitialization instance = new EagerInaitialization();
  
      private EagerInaitialization() {
      }
  
      public static EagerInaitialization getInstance() {
          return instance;
      }
  }
  ```
  
<br/>
<br/>

> ##  Double Checked Locking

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
  public class DoubleCheckedLocking {
      private volatile static DoubleCheckedLocking instance;
  
      private DoubleCheckedLocking(){}
  
      public static DoubleCheckedLocking getInstance(){
          if (instance == null){
              synchronized (DoubleCheckedLocking.class){
                  if(instance==null){
                      instance = new DoubleCheckedLocking();
                  }
              }
          }
  
          return instance;
      }
  }
  ```

<br/>
<br/>

> ## Lazy Holder

- 현 시점 가장 완벽한 방법.
- inner class 특징인 호출 되기 전 참조 되지 않는 방식, static 특징인 한번만 호줄 하는 방식, final 키워드를 이용한 불변성 보장 등을 이용함.

  ```java
  public class LazyHolder {
  
      private static class LazyHolderInner {
          private final static LazyHolder INSTANCE = new LazyHolder();
      }
  
      public static LazyHolder getInstance() {
          return LazyHolderInner.INSTANCE;
      }
  }
  ```

<br/>
<br/>