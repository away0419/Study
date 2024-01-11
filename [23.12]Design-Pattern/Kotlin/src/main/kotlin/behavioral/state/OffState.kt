package behavioral.state

object OffState : State {
    override fun powerButtonPush(laptop: Laptop) {
        println("OFF -> ON")
        laptop.setState(OnState)
    }

    override fun typeButtonPush() {
        println("무반응")
    }

    override fun toString(): String {
        return "현재 상태 OFF"
    }


}