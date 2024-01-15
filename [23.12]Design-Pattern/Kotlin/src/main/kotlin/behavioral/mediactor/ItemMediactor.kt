package behavioral.mediactor

class ItemMediactor : Mediactor {

    private val list = mutableListOf<Adventurer>();

    fun addAdventurer(adventurer: Adventurer) {
        list.add(adventurer)
    }

    override fun forwardRequest(msg: String) {
        notice()
        list.forEach {
            print("${it.name} 에게 전달 -> ")
            it.receiveRequestToMediactor(msg)
        }
    }

    override fun notice() {
        println("[중재인 요청 전달 목록]")
    }
}