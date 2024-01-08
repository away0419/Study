package behavioral.command

fun main() {
    val button = Button();
    button.setCommand(HeaterCommand())
    button.action()

    println()
    button.setCommand(LampCommand())
    button.action()
}