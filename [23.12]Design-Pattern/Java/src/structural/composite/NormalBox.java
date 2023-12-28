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
