package behavioral.iterator

class HamburgerCollection(private val size: Int) : Collection {
    private val arr = arrayOfNulls<Hamburger>(size)
    private var index = 0

    fun add(hamburger: Hamburger) {
        if (index < arr.size) {
            arr[index++] = hamburger
        }
    }

    override fun iterator(): Iterator {
        return HamburgerIterator(arr)
    }
}