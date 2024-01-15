package structural.proxy

class ProtectiveProxy(val path: String, val user: String): Image{
    init{
        println("$path 경로 프록시 생성")
    }

    override fun showImage() {
        when(this.user){
            "관리자" -> {
                println("$user 접근 성공")
                val highImage = HighImage(this.path)
                highImage.showImage()
            }
            else -> println("$user 접근 불가")
        }


    }

}