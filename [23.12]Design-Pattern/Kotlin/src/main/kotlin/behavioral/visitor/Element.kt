package behavioral.visitor

fun interface Element {
    fun accept(visitor: Visitor)
}