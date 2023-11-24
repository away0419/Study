package Proxy;

public class Real_Image implements Image{

	private String fileName;
	
	public Real_Image(String fileName) {
		this.fileName = fileName;
		loadFromDisk(fileName);
	}
	
	private void loadFromDisk(String fileName) {
		System.out.println("Loading " + fileName);
	}
	
	@Override
	public void displayImage() {
		System.out.println("Displaying " + fileName);
	}

}
