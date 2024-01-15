package structural.flyweight

fun main() {
    Tree.Factory.getInstance("대나무")
    Tree.Factory.getInstance("대나무")
    Tree.Factory.getInstance("대나무")

    println()
    Tree.Factory.getInstance("소나무")
    Tree.Factory.getInstance("소나무")
    Tree.Factory.getInstance("소나무")

    println()
    Tree.Factory.getInstance("감나무")
    Tree.Factory.getInstance("감나무")
    Tree.Factory.getInstance("감나무")
}