package crawling;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import custom.excel.ExcelDTO;
import custom.excel.ExcelFunction;

public class Main {
	public static void main(String[] args) {
		System.setProperty(ConstUtil.WEB_DRIVER_ID, ConstUtil.WEB_DRIVER_PATH);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		WebDriver driver = new ChromeDriver(options);

		
		Crawling crawling = new Crawling();
		List<ExcelDTO> list = crawling.makeDirectNoticeList(driver);


		ExcelFunction excelFunction = new ExcelFunction();
		excelFunction.makeExcel("direct_notice", list);

		driver.quit();
	}
}
