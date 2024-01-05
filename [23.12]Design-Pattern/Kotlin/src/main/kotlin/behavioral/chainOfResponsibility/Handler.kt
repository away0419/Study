package behavioral.chainOfResponsibility

interface Handler {
    fun setNextHandler(handler: Handler)
    fun process(authority: String)
}