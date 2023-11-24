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
