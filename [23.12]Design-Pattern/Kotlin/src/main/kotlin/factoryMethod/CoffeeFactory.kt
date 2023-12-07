package factoryMethod

class CoffeeFactory : DrinkFactory {
    override fun makeDrink(): Drink {
        println("make Coffee")
        return Coffee()
    }
}