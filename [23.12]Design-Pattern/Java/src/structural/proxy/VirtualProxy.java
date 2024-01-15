package structural.proxy;

public class VirtualProxy implements Image {
    String path;


    public VirtualProxy(String path) {
        this.path = path;
        System.out.println("["+path +"] 경로의 프록시 생성");
    }

    @Override
    public void showImage() {
        HighImage highImage = new HighImage(this.path);
        highImage.showImage();
    }
}