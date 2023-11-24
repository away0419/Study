package custom.excel;

public class ExcelDTO {
	private String[] data;

	public String[] getData() {
		return data;
	}

	public void setData(String... data) {
		this.data = data;
	}

	public ExcelDTO(String... data) {
		super();
		this.data = data;
	}

}
