package structural.composite

class NormalItem(private val name: String, private val price: Int): Item {
    override fun getPrice():Int {
        return this.price
    }

    override fun getName(): String {
        return this.name
    }
}