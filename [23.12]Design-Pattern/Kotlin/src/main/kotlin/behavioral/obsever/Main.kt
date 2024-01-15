package behavioral.obsever

fun main() {
    val store = Store()
    val adventurer1 = Adventurer("전사")
    val adventurer2 = Adventurer("법사")
    val adventurer3 = Adventurer("도사")

    store.registerObserver(adventurer1)
    store.registerObserver(adventurer2)
    store.registerObserver(adventurer3)
    store.sendNotice("레어 아이템이 등록 되었습니다.")
    
    println()
    store.removeObserver(adventurer1)
    store.sendNotice("에픽 아이템이 등록되었습니다.")
}