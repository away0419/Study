package structural.proxy

class HighImage(
    val path:String
): Image {

    init {
        println("$path 경로 이미지 로딩")
    }

    override fun showImage() {
        println("$path 경로 이미지 출력")
    }
}