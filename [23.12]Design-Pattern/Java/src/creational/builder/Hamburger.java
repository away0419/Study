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
