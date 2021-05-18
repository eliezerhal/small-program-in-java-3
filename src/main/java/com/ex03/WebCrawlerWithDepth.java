package com.ex03;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class WebCrawlerWithDepth extends Thread {
    private static final int MAX_DEPTH = 2;
    private final HashSet<String> links;
    private int imageCounter = 0;
    private Integer id = 0;
    private DataBase db;
    private String url;

    public WebCrawlerWithDepth() {
        links = new HashSet<>();
    }

    public void getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                Elements imgOnPage = document.select("img");
                db.updateImgCount(id, imgOnPage.size());
                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public void insertData(Integer id1, DataBase db1, String url1){
        id = id1;
        db = db1;
        url = url1;
    }

    public void run() {getPageLinks(url, 0);}

    /*public static void main(String[] args) {
        new WebCrawlerWithDepth().getPageLinks("http://www.mkyong.com/", 0);
    }*/
}