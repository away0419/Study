package creational.prototpye;

import java.util.ArrayList;
import java.util.List;

public class Drink implements Cloneable {
    private List<Integer> list = new ArrayList<>();

    public Drink(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        List<Integer> copyList = new ArrayList<>(list);
        return new Drink(copyList);
    }

    @Override
    public String toString() {

        return "Drink{" +
                "list = " + System.identityHashCode(list) + list +
                '}';
    }
}
