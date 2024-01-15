package structural.bridge

fun main() {
    val startButton = StartButton(Red())
    val endButton = EndButton(Blue())

    println("StartButton")
    startButton.color.getColor()
    startButton.action()

    println("----------------")

    println("StartButton")
    endButton.color.getColor()
    endButton.action()

}