package creational.abstractFactory;

public class BurgerSetFactoryImpl implements BurgerSetFactory{
    @Override
    public BurgerSet makeSet(String type) {
        BurgerSet burgerSet = null;
        switch (type){
            case "BurgerKing" -> burgerSet = new BurgerSet(new BurgerKingHamburger(), new BurgerKingDrink());
            case "Macdonald" -> burgerSet = new BurgerSet(new MacdonaldHamburger(), new MacdonaldDrink());
            default -> System.out.println("해당 버거 세트가 없음");
        }
        return burgerSet;
    }
}
