package behavioral.obsever

fun interface Observer {
    fun receiveNotice(msg: String);
}