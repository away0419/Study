package creational.dynamicFactory

fun main() {
    creational.dynamicFactory.DynamicFactory.createFood("Hamburger")
    println("----------------------------------------")
    creational.dynamicFactory.DynamicFactory.createFood("Drink")
    println("----------------------------------------")
    creational.dynamicFactory.DynamicFactory.createFood("Pizza")
}