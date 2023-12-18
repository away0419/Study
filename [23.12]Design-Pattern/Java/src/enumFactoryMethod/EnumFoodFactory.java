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
