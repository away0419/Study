package structural.adaptor;

import structural.Car;

public class Main {
    public static void main(String[] args) {
        FlyCar1 flyCar1 = new FlyCar1(new Car());
        flyCar1.start();
        flyCar1.fly();
        flyCar1.end();

        System.out.println("---------------");

        FlyCar2 flyCar2 = new FlyCar2();
        flyCar2.start();
        flyCar2.fly();
        flyCar2.end();
    }
}
