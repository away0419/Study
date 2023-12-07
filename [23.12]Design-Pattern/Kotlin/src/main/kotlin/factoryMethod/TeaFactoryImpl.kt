package factoryMethod

class TeaFactoryImpl : DrinkFactory{
    override fun makeDrink(): Drink {
        println("make Tea")
        return Tea()
    }
}