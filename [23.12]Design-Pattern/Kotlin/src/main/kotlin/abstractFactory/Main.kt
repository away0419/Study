package abstractFactory

fun main() {
    val burgerSetFactory = BurgerSetFactoryImpl()

    burgerSetFactory.makeBurgerSet("Macdonald")
    println("------------------------------------------")
    burgerSetFactory.makeBurgerSet("BurgerKing")

}