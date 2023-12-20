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
