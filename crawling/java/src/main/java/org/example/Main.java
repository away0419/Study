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