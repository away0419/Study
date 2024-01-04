package structural.proxy

class VirtualProxy(val path:String): Image {

    init{
        println("$path 경로 프록시 생성")
    }
    
    override fun showImage() {
        val highImage = HighImage(this.path)
        highImage.showImage()    
    }
}