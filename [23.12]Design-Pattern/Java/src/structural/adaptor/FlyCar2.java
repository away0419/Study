package structural.adaptor;

import structural.Car;

public class FlyCar2 extends Car implements Wing {

    public FlyCar2(){
        System.out.println("make FlyCar2");
    }

    @Override
    public void fly() {
        System.out.println("날기");
    }
}