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
    public int imageCounter = 0;
    private Integer id;
    private DataBase db;
    private String url;
    public boolean dead = false;

    /**
     * This is the class constructor function
     * @param str Is the URL where the crawler begins the search
     */
    public WebCrawlerWithDepth(String str) {
        url = str;
        links = new HashSet<>();
    }

    /**
     * This recursive function returns links of pages that crawler found
     * @param URL Is the url where the crawler is supposed to look now
     * @param depth Is the depth where the crawler is now
     */
    public void getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                Elements imgOnPage = document.select("img");
                //db.updateImgCount(id, imgOnPage.size());
                imageCounter += imgOnPage.size();
                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    /*public void insertData(Integer id1, DataBase db1, String url1){
        id = id1;
        db = db1;
        url = url1;
    }*/

    /**
     * This function runs the threads
     */
    public void run() {
        getPageLinks(url, 0);
        dead = true;
    }

    /**
     * This function returns the counter of images of the thread
     * @return the counter of images of the thread
     */
    public synchronized int getImgCount() {
        return imageCounter;
    }

    /**
     * This function checking if the thread is dead or not
     * @return true if the thread is dead and false if it's not
     */
    public synchronized boolean checkingLiveness () {
        return dead;
    }

    /*public static void main(String[] args) {
        new WebCrawlerWithDepth().getPageLinks("http://www.mkyong.com/", 0);
    }*/
}