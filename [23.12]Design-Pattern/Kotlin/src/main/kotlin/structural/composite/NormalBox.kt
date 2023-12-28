package structural.composite

class NormalBox(private val name: String, private val price: Int) : Box {
    private val list = mutableListOf<Item>()

    override fun getAllPrice(): Int = list.sumOf {
        when (it) {
            is Box -> it.getAllPrice() + it.getPrice()
            else -> it.getPrice()
        }
    }

    override fun getItems(): String = "$name = { ${list.joinToString(", ") { item ->
        when (item) {
            is Box -> item.getItems()
            else -> item.getName()
        }
    }} }"

    override fun addItem(item: Item) {
        list.add(item)
    }

    override fun removeItem(item: Item) {
        list.remove(item)
    }

    override fun getPrice(): Int = price

    override fun getName(): String = name
}
