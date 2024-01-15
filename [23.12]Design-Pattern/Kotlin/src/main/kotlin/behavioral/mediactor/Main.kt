package behavioral.mediactor

fun main() {
    val adventurer1 = Adventurer("전사")
    val adventurer2 = Adventurer("마법사")
    val adventurer3 = Adventurer("도적")
    val itemMediactor = ItemMediactor()

    adventurer1.setMediactor(itemMediactor)
    adventurer2.setMediactor(itemMediactor)
    adventurer3.setMediactor(itemMediactor)

    adventurer1.sendRequestToMediactor("전사가 보내는 아이템")
}