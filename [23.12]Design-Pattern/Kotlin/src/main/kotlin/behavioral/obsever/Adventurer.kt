package behavioral.obsever

class Adventurer(private val name: String): Observer {
    override fun receiveNotice(msg: String) {
        println("${name}님 메시지가 도작했습니다. 내용: $msg")
    }
}