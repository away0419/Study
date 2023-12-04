> ## Jsoup

- 정적 수집만 가능.
- Selenium 보다 빠름.
- 수집하려는 데이터의 uri가 일정한 패턴으로 반복 될 때 사용.

<br>
<br>

> ## Selenium

- 동적 수집도 가능.
- Jsoup 보다 느림.
- 로그인, 버튼 클릭 등 동작 이후 화면의 데이터가 바뀔 때 사용.

<br>
<br>

> ## Excel 출력

- 수집한 데이터 출력.
- 확장자명(.xls, .xlsx)에 따라 필요한 라이브러리가 다름.

<br>
<br>

> ## 라이브러리

<details>
    <summary>gradle</summary>

- 버전에 따라 명령어가 다를 수 있음.

  ```gradle
  implementation 'org.jsoup:jsoup:1.14.2'
  implementation group: 'org.seleniumhq.selenium', name: 'selenium-java', version: '4.8.3'
  implementation group: 'org.apache.poi', name: 'poi', version: '5.0.0'
  implementation group: 'org.apache.poi', name: 'poi-ooxml', version: '5.0.0'
  ```

  </details>

<br>
<br>

> ## 크롤링

<details>
    <summary>ConstUtil</summary>

- 크롤링 대상 URL.
- 드라이버 URL.

  ```java
  package org.example;

  public interface ConstUtil {
      String WEB_DRIVER_ID = "webdriver.chrome.driver";
      String WEB_DRIVER_PATH = "C:\\Users\\KTDS\\Downloads\\chromedriver_win32\\chromedriver.exe";
      String NOTICE_BASE_URL = "https://www.lina.co.kr/cms/customer/notice/index,1,list1,";
      int NOTICE_NUM_PAGES = 56;
      String NOTICE_ONE_URL = "https://www.lina.co.kr/cms/customer/notice/index.htm";
      String DIRECT_NOTICE_ONE_URL = "https://direct.lina.co.kr/cms/web/notice/noticeList";
      String DIRECT_NOTICE_BASE_URL = "https://direct.lina.co.kr/cms/web/notice/noticeList,1,list1,";
      int DIRECT_NOTICE_NUM_PAGES = 11;
  }
  ```

  </details>

<details>
    <summary>ExcelDTO</summary>

- Excel 정보를 담을 객체.
- 칼럼이 몇개일지 모르기 때문에 가변 변수 사용.

  ```java
  package org.example;

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
  ```

</details>

<details>
    <summary>ExcelFunction</summary>

- Excel 만들기 기능 구현.

  ```java
  package org.example;

  import java.io.FileOutputStream;
  import java.io.IOException;
  import java.util.List;
  import java.util.stream.IntStream;

  import org.apache.poi.xssf.usermodel.XSSFRow;
  import org.apache.poi.xssf.usermodel.XSSFSheet;
  import org.apache.poi.xssf.usermodel.XSSFWorkbook;
  import org.example.ExcelDTO;

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
  ```

</details>

<details>
    <summary>Crawling</summary>

- 크롤링 기능 구현.
- 데이터 수집 후 ExcelDTO로 변환.

  ```java
  package org.example;

  import java.util.ArrayList;
  import java.util.List;

  import org.openqa.selenium.By;
  import org.openqa.selenium.By.ByXPath;
  import org.openqa.selenium.WebDriver;
  import org.openqa.selenium.WebElement;


  public class Crawling {

      //브라우저 드라이버를 받고 이를 이용하여 공지사항에서 데이터를 크롤링한 뒤 ExcelDTO에 저장
      public List<ExcelDTO> makeNoticeList(WebDriver driver) {
          // 공지사항에 있는 모든 글의 URL을 가져옴.
          List<String> boardUrlList = new ArrayList<>();
          // 해당 글의 조회수를 가져옴
          List<String> reviewCntList = new ArrayList<>();

          int idx = 11;
          // 주어진 페이지 만큼 반복
          for (int page = 2; page <= ConstUtil.NOTICE_NUM_PAGES; page++) {
              String url = ConstUtil.NOTICE_BASE_URL + page + ".htm";

              // 해당 URL을 driver로 실행
              driver.get(url);
              // 해당 페이지에서 클래스 명이 tal인 태그 모두 수집
              List<WebElement> el1 = driver.findElements(By.className("tal"));
              System.out.println(url);

              for (WebElement el : el1) {
                  // 태그가 a인 첫번째 요소를 가져오고 해당 태드 안에 있는 속성 중 하나인 href의 값을 가져옴.
                  String str = el.findElement(By.tagName("a")).getAttribute("href");
                  String reviewCnt = driver.findElement(By.className("Nview"+idx++)).getText();
                  reviewCntList.add(reviewCnt);
                  boardUrlList.add(str);

              }
          }


          // ExcelDTO를 담을 변수
          List<ExcelDTO> list = new ArrayList<>();
          idx = 0;
          for (String url : boardUrlList) {
              // 해당 URL의 정보를 가져온다.
              driver.get(url);
              List<WebElement> el1 = driver.findElements(By.tagName("tr"));
              List<WebElement> imgList = el1.get(2).findElement(By.tagName("td")).findElements(By.tagName("img"));

              String imgArr[] = new String[imgList.size() + 5];
              imgArr[0] = url;
              imgArr[1] = reviewCntList.get(idx++);
              imgArr[2] = el1.get(0).findElement(By.tagName("td")).getText();
              imgArr[3] = el1.get(1).findElement(By.tagName("td")).getText();
              // innerHTML의 경우 현재 태그 안에 있는 모든 태그와 텍스트값을 그대로 가져옴.
              imgArr[4] = el1.get(2).findElement(By.tagName("td")).getAttribute("innerHTML").trim();
              for (int i = 5; i < imgList.size() + 5; i++) {
                  imgArr[i] = imgList.get(i - 5).getAttribute("src").substring(22);
              }

              ExcelDTO dto = new ExcelDTO(imgArr);
              list.add(dto);
              System.out.println(idx);
          }

          return list;
      }


      public List<ExcelDTO> makeDirectNoticeList(WebDriver driver) {
          List<String> boardUrlList = new ArrayList<>();

          for (int page = 2; page <= ConstUtil.DIRECT_NOTICE_NUM_PAGES; page++) {
              String url = ConstUtil.DIRECT_NOTICE_BASE_URL + page;

              driver.get(url);
              List<WebElement> el1 = driver.findElements(By.className("link"));
              System.out.println(url);

              for (WebElement el : el1) {
                  String str = el.getAttribute("href");
                  boardUrlList.add(str);
                  System.out.println(str);
              }
          }


          List<ExcelDTO> list = new ArrayList<>();
          int a = 97;
          for (String url : boardUrlList) {
              driver.get(url);

              String imgArr[] = new String[5];
              imgArr[0] = url;
              imgArr[1] = driver.findElement(By.className("tit")).getText();
              imgArr[2] = driver.findElement(By.className("date")).getText();
              imgArr[3] = driver.findElement(By.className("bbs_view_cont")).findElement(ByXPath.xpath("..")).getAttribute("innerHTML");
              imgArr[4] = String.valueOf(a--);

              ExcelDTO dto = new ExcelDTO(imgArr);
              list.add(dto);
          }

          return list;
      }

  }
  ```

</details>

<details>
    <summary>Main</summary>

- Jsoup 이용하여 html 파일에서 데이터 크롤링.
- Selenium을 이용하여 Web에서 크롤링.
- 상황에 맞게 Jsoup, Selenium 사용하면 됨.

  ```java
  package org.example;

  import org.jsoup.Jsoup;
  import org.jsoup.nodes.Document;
  import org.jsoup.nodes.Element;
  import org.jsoup.nodes.TextNode;
  import org.jsoup.select.Elements;

  import java.io.File;
  import java.io.FileWriter;
  import java.io.IOException;
  import java.nio.file.Files;
  import java.nio.file.Path;
  import java.nio.file.Paths;
  import java.util.List;

  public class Main {
      public static void main(String[] args) throws IOException {
          String folderPath = "D:\\파일경로";
          List<File> htmlFiles = Files.walk(Paths.get(folderPath))
                  .filter(Files::isRegularFile)
                  .map(Path::toFile)
                  .filter(file -> file.getName().endsWith(".html"))
                  .toList();

          for (int i = 0; i < 1; i++) {
              Document document = Jsoup.parse(htmlFiles.get(i), "UTF-8");
              System.out.println(htmlFiles.get(i).getName());
              fn1(document);
              fn2(document);
              fn4(document);
              saveHTML(document, htmlFiles.get(i).getName());
          }

      }

      public static void fn1(Document document) {
          Elements elements = document.select("dl");

          for (Element el :
                  elements) {
              Elements olEl = el.select("ol");
              olEl.remove();

              Element newDiv = new Element("div").appendChildren(olEl);
              newDiv.addClass("pvc_div_type01");
              el.after(newDiv);
          }

          for (Element el :
                  elements) {
              Elements ulEl = el.select("ul");
              ulEl.remove();

              Element newDiv = new Element("div").appendChildren(ulEl);
              newDiv.addClass("pvc_div_type01");
              el.after(newDiv);
          }

      }

      public static void fn2(Document document) {
          Elements elements = document.select("li");

          for (Element el :
                  elements) {
              Elements pEls = el.select("p");

              for (Element pEl :
                      pEls) {
                  String content = pEl.text();

                  TextNode textNode = new TextNode(content);
                  pEl.replaceWith(textNode);
              }
          }
      }

      public static void fn4(Document document) {
          Elements elements = document.select("ul[class]");

          for (Element el :
                  elements) {
              String ulClass = el.className();

              Elements liEls = el.select("li");

              for (Element liEl :
                      liEls) {
                  liEl.addClass(ulClass);
              }
              el.removeClass(ulClass);
          }
      }

      public static void saveHTML(Document document, String name) {
          String content = document.html();
          String outputPath = "D:\\파일경로\\" + name;

          try (FileWriter fileWriter = new FileWriter(outputPath)) {
              fileWriter.write(content);
          } catch (IOException e) {
              e.printStackTrace();
          }

      }
  }
  ```

</details>
