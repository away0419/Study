package structural.facade;

public class Facade {
    public void action(){
        Person person = new Person();
        Tv tv = new Tv();
        Pizza pizza = new Pizza();

        person.move();
        pizza.addTopping();
        person.move();
        tv.ON();
        person.watch();
    }
}
