package structural.proxy;

public class ProtectiveProxy implements Image{
    String path;
    String authority;

    public ProtectiveProxy(String path, String authority) {
        this.path = path;
        this.authority = authority;
        System.out.println("["+path +" 경로, "+authority+"사용자] 프록시 생성");
    }

    @Override
    public void showImage() {
        if(this.authority.equals("관리자")){
            System.out.println("관리자 접근");
            HighImage highImage = new HighImage(this.path);
            highImage.showImage();
        }else {
            System.out.println(this.authority + "는 접근할 수 없습니다.");
        }
    }



}
