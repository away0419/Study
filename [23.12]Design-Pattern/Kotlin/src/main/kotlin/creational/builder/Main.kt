package creational.builder

fun main() {
    val drink = Drink(size = "라지", price = "10000", name = "커피")
    val hamburger = Hamburger.Builder().name("맥도날드버거").size("라지").price("10000").build()

    println(drink)
    println("----------------------------")
    println(hamburger)
}