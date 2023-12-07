package factoryMethod

class DrinkFactoryImpl : DrinkFactory {
    override fun makeDrink(): Drink {
        println("make Drink")
        return Drink()
    }
}