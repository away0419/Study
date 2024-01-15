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
