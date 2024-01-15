package behavioral.memento;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Memento> mementos = new Stack<>();
        Adventurer adventurer = new Adventurer("전사", 10);

        System.out.println("초기 정보: " + adventurer.toString());

        mementos.push(adventurer.createMemento());
        adventurer.level = 12;
        adventurer.job = "법사";

        System.out.println("변경 후: " + adventurer.toString());

        adventurer.setInfo(mementos.pop());

        System.out.println("되돌리기: " + adventurer.toString());
    }
}
