package structural.composite;

public interface Box  extends Item{
    void addItem(Item item);
    void removeItem(Item item);
    int getAllPrice();
    String getItems();
}
