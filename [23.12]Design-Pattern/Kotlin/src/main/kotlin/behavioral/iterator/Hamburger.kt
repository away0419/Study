package behavioral.iterator

class Hamburger(private val price:Int, private val name: String) {
    override fun toString(): String {
        return "Hamburger(price=$price, name='$name')"
    }
}