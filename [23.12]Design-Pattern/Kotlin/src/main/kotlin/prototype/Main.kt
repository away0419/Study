package prototype

fun main() {
    val list = listOf(1, 2, 3, 4, 5)
    val drink = Drink(list)
    val copyDrink = drink.copy()

    println(drink)
    println("-------------")
    println(copyDrink)
}