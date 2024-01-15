package behavioral.strategy

fun main() {
    val adventurer1 = Adventurer(Magic())
    val adventurer2 = Adventurer(Fence())

    adventurer1.useSkill()
    println()
    adventurer2.useSkill()

}