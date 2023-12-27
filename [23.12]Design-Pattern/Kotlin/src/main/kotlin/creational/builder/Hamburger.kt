package creational.builder

class Hamburger private constructor(
    val name: String,
    val size: String,
    val price: String
) {
    // 중첩 클래스로 정적 이너 클래스와 비슷한 개념이다.
    class Builder(
        private var name: String = "",
        private var size: String = "",
        private var price: String = "",
        ) {
        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun size(size: String): Builder {
            this.size = size
            return this
        }

        fun price(price: String): Builder {
            this.price = price
            return this
        }

        fun build(): Hamburger {
            return Hamburger(name, size, price)
        }
    }

    override fun toString(): String {
        return "Hamburger(name='$name', size='$size', price='$price')"
    }


}