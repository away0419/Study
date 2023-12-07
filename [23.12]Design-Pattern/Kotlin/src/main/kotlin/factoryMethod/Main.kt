package factoryMethod

fun main() {
    val drinkFactory = DrinkFactoryImpl()
    val coffeeFactory = CoffeeFactory()
    val teaFactory = TeaFactoryImpl()

    drinkFactory.makeDrink()
    coffeeFactory.makeDrink()
    teaFactory.makeDrink()

}
