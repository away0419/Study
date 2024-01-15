package behavioral.template

fun main() {
    val adventurer1: Adventurer = Warrior()
    val adventurer2: Adventurer = Wizard()

    adventurer1.attack()
    println()
    adventurer2.attack()

}