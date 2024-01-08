package behavioral.interpreter

class Number(private val double: Double): Expression {

    override fun interpret(): Double {
        return this.double
    }
}