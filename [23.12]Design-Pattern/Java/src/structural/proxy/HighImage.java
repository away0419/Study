package structural.proxy;

public class HighImage implements Image{
    String path;

    public HighImage(String path) {
        System.out.println(path + " 경로의 이미지 로딩");
        this.path = path;
    }


    @Override
    public void showImage() {
        System.out.println(path+ " 경로의 이미지 출력");
    }
}
