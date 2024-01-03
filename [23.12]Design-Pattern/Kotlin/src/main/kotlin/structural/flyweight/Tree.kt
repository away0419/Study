package structural.flyweight

class Tree private constructor(
    val type: Model,
    val x: Double,
    val y: Double
) {
    companion object Factory {
        fun getInstance(type: String): Tree {
            val model = Model.Factory.getInstance(type)
            val x = Math.random() * 10000
            val y = Math.random() * 10000

            println("$type 위치: x=${x}, y=${y}")
            return Tree(model, x, y)
        }
    }
}