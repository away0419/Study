> ## 싱글톤 패턴 (생성)

- 스프링에서 Bean을 생성할 때, 싱글톤 패턴을 사용함.
- 스프링 싱글톤은 스프링 컨테이너에 (BeanFactory/ApplicationContext) 의해 구현됨.
- 인스턴스 생성 시점은 컨테이너에 따라 다름.
  - BeanFactory : 최초 호출 시점. (Lazy Loading)
  - ApplicationContext : 최초 실행 시점. (Eager Loading)
  - 기본적으로 ApplicationContext를 사용하므로 최초 실행시 생성 된다 생각하면 될 듯 함.
  - 만약 ApplicationContext 사용 할 때 특정 컴포넌트를 Lazy Loading을 하고 싶다면 @Lazy 사용하면 됨.
- 하나의 인스턴스이므로 해당 인스턴스의 변수를 수정하면 다른 서비스에서도 모두 바뀌는 당연한 사실을 잊지 않게 조심 해야 함.

> ## Factory Method (생성)

- 싱글톤 패턴이 팩토리 메소드 패턴을 사용하므로 Bean 등록에 팩토리 메소드 패턴이 사용된다 볼 수 있음.
