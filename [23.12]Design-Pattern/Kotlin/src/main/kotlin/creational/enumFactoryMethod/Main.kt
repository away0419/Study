package creational.enumFactoryMethod

fun main() {
    println(FoodFactory.DRINK.name)
    println(FoodFactory.DRINK.foodName)
    FoodFactory.DRINK.createFood()
    println("-----------------------------")
    println(FoodFactory.HAMBURGER.name)
    println(FoodFactory.HAMBURGER.foodName)
    FoodFactory.HAMBURGER.createFood()
}