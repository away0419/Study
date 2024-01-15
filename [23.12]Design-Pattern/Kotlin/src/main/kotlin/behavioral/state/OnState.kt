package behavioral.state

object OnState : State {
    override fun powerButtonPush(laptop: Laptop) {
        println("ON -> OFF")
        laptop.setState(OffState)
    }

    override fun typeButtonPush() {
        println("타자 입력")
    }

    override fun toString(): String {
        return "현재 상태 ON"
    }


}