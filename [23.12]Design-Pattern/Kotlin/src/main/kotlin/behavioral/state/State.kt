package behavioral.state

interface State {
    fun powerButtonPush(laptop: Laptop)
    fun typeButtonPush()
}