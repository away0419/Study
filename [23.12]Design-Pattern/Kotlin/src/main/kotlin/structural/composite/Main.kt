package structural.composite

fun main() {
    val normalBox = NormalBox("기본 상자", 50000)
    val equipmentBox = NormalBox("장비 상자", 1000)
    val sword = NormalItem("검", 10000)
    val shield = NormalItem("방패", 10000)
    val gold = NormalItem("금", 200000)

    equipmentBox.addItem(sword)
    equipmentBox.addItem(shield)
    normalBox.addItem(equipmentBox)
    normalBox.addItem(gold)

    println(normalBox.getItems())
    println(normalBox.getAllPrice())

}