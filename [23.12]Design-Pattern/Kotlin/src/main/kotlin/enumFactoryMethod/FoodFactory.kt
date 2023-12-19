package enumFactoryMethod

enum class FoodFactory(
    val foodName: String
) {
    DRINK("음료수"){
        override fun createFood(): Food {
            return Drink()
        }
    },
    HAMBURGER("햄버거"){
        override fun createFood(): Food {
            return Hamburger()
        }
    };
    abstract fun createFood(): Food
}