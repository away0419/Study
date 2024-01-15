package creational.abstractFactory

interface BurgerSetFactory {
    fun makeBurgerSet(type: String): BurgerSet?
}