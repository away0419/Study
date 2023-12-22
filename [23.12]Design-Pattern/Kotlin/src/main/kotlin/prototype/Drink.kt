package prototype

data class Drink(val list: List<Int>) {

    fun copy(): Drink{
        val copyList = list.toMutableList()
        return Drink(copyList)
    }
    
    override fun toString(): String {
        val hashCode = System.identityHashCode(list)
        return "Drink(list=$list, $hashCode)"
    }
}