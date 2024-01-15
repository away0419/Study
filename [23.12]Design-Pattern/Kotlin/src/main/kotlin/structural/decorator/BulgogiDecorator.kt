package structural.decorator

class BulgogiDecorator(private val hamburger: Hamburger): HamburgerDecorator(hamburger){
    override fun getName(): String {
        return "불고기 ${hamburger.getName()}"
    }
}
