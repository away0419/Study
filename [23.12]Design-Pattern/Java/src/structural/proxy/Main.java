package structural.proxy;

public class Main {
    public static void main(String[] args) {
        VirtualProxy virtualProxy1 = new VirtualProxy("1번경로");
        VirtualProxy virtualProxy2 = new VirtualProxy("2번경로");
        VirtualProxy virtualProxy3 = new VirtualProxy("3번경로");

        System.out.println();
        virtualProxy2.showImage();

        System.out.println("---------------------------------------\n");
        ProtectiveProxy protectiveProxy1 = new ProtectiveProxy("1번경로", "일반 사용자");
        ProtectiveProxy protectiveProxy2 = new ProtectiveProxy("1번경로", "관리자");

        System.out.println();
        protectiveProxy1.showImage();
        protectiveProxy2.showImage();




    }
}
