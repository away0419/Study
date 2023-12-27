package structural.Adaptor;

import structural.Car;

public class FlyCar1 implements Wing{
    private Car car;

    public FlyCar1(Car car){
        this.car = car;
        System.out.println("make FlyCar1");
    }

    public void start(){
        car.start();
    }

    public void end(){
        car.end();
    }

    @Override
    public void fly() {
        System.out.println("날기");
    }
}
