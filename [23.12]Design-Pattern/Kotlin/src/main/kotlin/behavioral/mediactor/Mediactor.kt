package behavioral.mediactor

interface Mediactor {
    fun forwardRequest(msg: String)
    fun notice()
}