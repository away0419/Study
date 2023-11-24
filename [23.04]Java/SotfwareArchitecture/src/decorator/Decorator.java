package decorator;

abstract public class Decorator implements Component {
    private Component coffeeComponent;
    
    public Decorator(Component coffeeComponent) {
        this.coffeeComponent = coffeeComponent;
    }
    
    public String add() {
        return coffeeComponent.add();
    }
}
