package creational.abstractFactory

class BurgerSetFactoryImpl: BurgerSetFactory {
    override fun makeBurgerSet(type: String): BurgerSet?{
        var burgerSet: BurgerSet? = null

        when(type){
            "Macdonald" -> burgerSet = BurgerSet(MacdonaldBurger(), MacdonaldDrink())
            "BurgerKing" -> burgerSet = BurgerSet(BurgerKingBurger(), BurgerKingDrink())
        }

        return burgerSet
    }
}