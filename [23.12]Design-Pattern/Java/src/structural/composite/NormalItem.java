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
