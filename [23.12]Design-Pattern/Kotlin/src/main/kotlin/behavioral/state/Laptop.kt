package behavioral.state

class Laptop {
    private var state: State = OffState

    fun setState(state: State) {
        this.state = state
    }

    fun powerButtonPush() {
        state.powerButtonPush(this)
    }

    fun typeButtonPush() {
        state.typeButtonPush()
    }

    fun currentState() {
        println(state.toString())
    }


}