package structural.proxy

fun main() {
    val virtualProxy1 = VirtualProxy("경로1")
    val virtualProxy2 = VirtualProxy("경로2")

    println()
    virtualProxy1.showImage()

    println("------------------------")
    val protectiveProxy1 = ProtectiveProxy("경로1", "관리자")
    val protectiveProxy2 = ProtectiveProxy("경로2", "사용자")

    println()
    protectiveProxy1.showImage()
    println()
    protectiveProxy2.showImage()





}