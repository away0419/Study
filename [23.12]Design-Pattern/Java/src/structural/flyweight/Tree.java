package structural.flyweight;

public class Tree {
    Model model;
    double x;
    double y;

    private Tree(Model model, double x, double y) {
        this.model = model;
        this.x = x;
        this.y = y;
    }

    public static class Factory {
        public static Tree getInstance(String type) {
            Model model = Model.Factory.getInstance(type);
            double x = Math.random() * 10000;
            double y = Math.random() * 10000;

            System.out.println(type + "의 좌표: x=" + x + ", y=" + y);
            return new Tree(model, x, y);
        }
    }
}
