package Proxy;

public class Main {
	public static void main(String[] args) {
		Image image1 = new Proxy_Image("test1.png");
		Image image2 = new Proxy_Image("test1.png");
	
		image1.displayImage();
		System.out.println();
		image2.displayImage();
	}
}
