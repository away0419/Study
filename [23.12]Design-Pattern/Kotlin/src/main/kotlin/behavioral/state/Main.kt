package behavioral.state

fun main() {
    val laptop = Laptop()

    println("[초기 상태]")
    laptop.currentState()
    laptop.typeButtonPush()

    println()
    println("[전원 버튼 클릭]")
    laptop.powerButtonPush()
    laptop.currentState()
    laptop.typeButtonPush()

    println()
    println("[전원 버튼 클릭]")
    laptop.powerButtonPush()
    laptop.currentState()
    laptop.typeButtonPush()

}