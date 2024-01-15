package structural.decorator

class CheeseDecorator(
    val hamburger: Hamburger
): HamburgerDecorator(hamburger) {
    override fun getName(): String {
        return "치즈 ${super.getName()}"
    }
}