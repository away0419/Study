package builder;

public class DrinkBuilder {
    private String name;
    private String size;
    private String price;

    public DrinkBuilder name(String name){
        this.name = name;
        return this;
    }

    public DrinkBuilder size(String size){
        this.size = size;
        return this;
    }

    public DrinkBuilder price(String price){
        this.price = price;
        return this;
    }

    public Drink build(){
        return new Drink(this.name, this.size, this.price);
    }
}
