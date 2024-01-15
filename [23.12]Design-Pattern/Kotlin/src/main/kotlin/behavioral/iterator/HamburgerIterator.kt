package behavioral.iterator

class HamburgerIterator(private val arr: Array<Hamburger?>): Iterator {
    private var index = 0

    override fun hasNext(): Boolean {
        return index < arr.size
    }

    override fun next(): Any? {
        return arr[index++]
    }
}