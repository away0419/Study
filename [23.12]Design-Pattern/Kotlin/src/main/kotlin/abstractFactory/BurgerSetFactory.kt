package abstractFactory

interface BurgerSetFactory {
    fun makeBurgerSet(type: String):BurgerSet?
}