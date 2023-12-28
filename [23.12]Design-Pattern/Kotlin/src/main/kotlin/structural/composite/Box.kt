package structural.composite

interface Box:Item {
    fun getAllPrice(): Int
    fun getItems(): String
    fun addItem(item: Item)
    fun removeItem(item: Item)
}