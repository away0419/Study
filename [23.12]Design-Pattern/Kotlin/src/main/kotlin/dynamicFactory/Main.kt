package dynamicFactory

fun main() {
    DynamicFactory.createFood("Hamburger")
    println("----------------------------------------")
    DynamicFactory.createFood("Drink")
    println("----------------------------------------")
    DynamicFactory.createFood("Pizza")
}