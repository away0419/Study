package builder

class DrinkBuilder (
    private var name: String = "",
    private var size: String = "",
    private var price: String = ""
) {
    fun name(name: String): DrinkBuilder {
        this.name = name
        return this
    }
    fun size(size: String): DrinkBuilder {
        this.size = size
        return this
    }

    fun price(price: String): DrinkBuilder {
        this.price = price
        return this
    }

    fun build(): Drink{
        return Drink(name, size, price)
    }
}