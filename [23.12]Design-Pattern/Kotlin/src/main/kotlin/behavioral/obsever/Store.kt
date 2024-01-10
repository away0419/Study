package behavioral.obsever

class Store: Subject {
    private val subscribers = mutableListOf<Observer>()

    override fun registerObserver(observer: Observer) {
        subscribers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        subscribers.remove(observer)
    }

    override fun sendNotice(msg: String) {
        println("[구독자 송신 목록]")
        subscribers.forEach{
            it.receiveNotice(msg)
        }
    }
}