최종 작성일 : 23.04.20
# Jsoup
- 정적 수집만 가능
- Selenium에 비해 빠름.

<br>

## 라이브러리 ([링크](https://mvnrepository.com/artifact/org.jsoup/jsoup))

- gradle 

    ``` 
    implementation 'org.jsoup:jsoup:1.14.2'
    ```



<br>
<br>

# Selenium
- 동적 수집도 가능 (클릭하여 화면이 바뀔 경우에도 수집 가능)
- Jsoup에 비해 느림

<br>

## 라이브러리 ([링크](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java))

- gradle 

    ``` 
    implementation group: 'org.seleniumhq.selenium', name: 'selenium-java', version: '4.8.3'
    ```

<br>
<br>

# Excel
- 수집한 데이터 출력.
- 확장자명(.xls, .xlsx)에 따라 필요한 라이브러리가 다름.

<br>

## 라이브러리 ([링크1](https://mvnrepository.com/artifact/org.apache.poi/poi/5.2.3), [링크2](https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml/5.2.3))

- gradle 

    ``` 
    implementation group: 'org.apache.poi', name: 'poi', version: '5.0.0'
    implementation group: 'org.apache.poi', name: 'poi-ooxml', version: '5.0.0' 
    ```