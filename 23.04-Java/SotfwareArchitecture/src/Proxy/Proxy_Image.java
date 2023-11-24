package Proxy;

public class Proxy_Image implements Image{
	private Real_Image realImage;
	private String fileName;
	
	public Proxy_Image(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void displayImage() {
		if(realImage==null) {
			realImage = new Real_Image(fileName);
		}
		realImage.displayImage();
	}

}
