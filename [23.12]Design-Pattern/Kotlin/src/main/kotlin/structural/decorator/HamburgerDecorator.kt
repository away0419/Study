package structural.decorator

abstract class HamburgerDecorator(private val hamburger: Hamburger): Hamburger {
    override fun getName(): String {
       return hamburger.getName()
    }
}