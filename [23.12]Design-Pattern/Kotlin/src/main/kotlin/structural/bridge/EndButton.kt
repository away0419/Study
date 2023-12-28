package structural.bridge

class EndButton(color: Color): Button(color) {
    override fun action() {
        println("End!!!")
    }
}