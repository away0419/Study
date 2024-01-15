package structural.adapter

fun main() {
    val flyCar1 = FlyCar1(Car())
    val flyCar2 = FlyCar2()

    println("flayCar1")
    flyCar1.start()
    flyCar1.fly()
    flyCar1.end()

    println("---------------------")

    println("flayCar2")
    flyCar2.start()
    flyCar2.fly()
    flyCar2.end()
}