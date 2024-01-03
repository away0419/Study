package structural.flyweight;

public class Main {
    public static void main(String[] args) {
        Tree.Factory.getInstance("소나무");
        Tree.Factory.getInstance("소나무");
        Tree.Factory.getInstance("소나무");

        System.out.println();

        Tree.Factory.getInstance("대나무");
        Tree.Factory.getInstance("대나무");
        Tree.Factory.getInstance("대나무");

        System.out.println();

        Tree.Factory.getInstance("벚꽃 나무");
        Tree.Factory.getInstance("벚꽃 나무");
        Tree.Factory.getInstance("벚꽃 나무");

    }
}
