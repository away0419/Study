package builder

class Drink(
    val name: String,
    val size: String,
    val price: String
){
    override fun toString(): String {
        return "Drink(name='$name', size='$size', price='$price')"
    }
}