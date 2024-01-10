package behavioral.mediactor

class Adventurer(val name: String) {
    private var itemMediactor: ItemMediactor? = null

    fun setMediactor(itemMediactor: ItemMediactor) {
        this.itemMediactor = itemMediactor
        itemMediactor.addAdventurer(this)
    }

    fun sendRequestToMediactor(msg: String) {
        itemMediactor?.forwardRequest(msg)
    }

    fun receiveRequestToMediactor(msg: String) {
        println("전달 받은 내용: $msg")
    }

}