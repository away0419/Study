package custom.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFunction {
	//Excel 저장할 위치
	private final String saveBasicURL = "C:/Users/KTDS/Downloads/chromedriver_win32/";

	//엑셀 네임을 받아 해당 네임으로 된 파일을 생성함.
	//list에는 엑셀에 넣을 데이터를 가진 ExcelDTO가 있음.
	public void makeExcel(String excelName, List<ExcelDTO> list) {

		// saveBasicURL 경로에 excelName.xlsx를 생성
		try (FileOutputStream fos = new FileOutputStream(saveBasicURL + excelName + ".xlsx")) {

			// Excel 객체 생성
			XSSFWorkbook workbook = new XSSFWorkbook();
			// Excel 시트 생성
			XSSFSheet sheet = workbook.createSheet("시트1");

			// 시트의 필드에 데이터 넣기
			// list의 갯수 만큼 반복
			IntStream.range(0, list.size()).forEach(idx -> {
				//행 객체 생성
				final XSSFRow curRow = sheet.createRow(idx);
				// list의 해당 idx번째 excelDTO가 가진 데이터 가져오기
				String[] datas = list.get(idx).getData();

				
				// datas의 길이만큼 반복.
				// 해당 index번째 칼럼에 데이터 저장하기
				IntStream.range(0, datas.length).forEach(index -> {
					try {
		            	curRow.createCell(index).setCellValue(datas[index]);
					} catch (Exception e) {
						curRow.createCell(index).setCellValue(datas[0]);
					}
				});
			});

			// Excel객체를 fos의 형태로 저장
			workbook.write(fos);
			System.out.println("Excel file created successfully");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create Excel file");
		}
	}
}
