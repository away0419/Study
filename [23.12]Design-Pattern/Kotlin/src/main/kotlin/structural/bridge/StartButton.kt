package structural.bridge

class StartButton(
    color: Color
): Button(color) {
    override fun action() {
        println("Start!!")
    }
}