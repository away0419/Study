package prototpye;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(2);
        list.add(1);

        Drink drink = new Drink(list);
        Drink copyDrink = (Drink) drink.clone();
        System.out.println(drink);
        System.out.println("------------------------------------");
        System.out.println(copyDrink);

    }
}
