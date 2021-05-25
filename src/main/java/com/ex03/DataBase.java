package com.ex03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a class that takes care of the program's data retention
 */
public class DataBase {
    //private int imgCount = 0;
    //private HashMap<Integer, Integer> mapCount = new HashMap<>();
    /**
     * This is array of crawlers
     */
    public List<WebCrawlerWithDepth> wc = new ArrayList<>();

    /**
     * This is the class constructor function
     */
    public DataBase() {}

    /**
     * This function adds crawlers to the array dynamically
     * @param crawler Is the new crawler which is added to the array
     */
    public void add(WebCrawlerWithDepth crawler) {
        wc.add(crawler);
    }
    //public int getImgCount(Integer id) {return mapCount.get(id);}
    //public void setImgCount(Integer id) {mapCount.put(id, 0);}
    //public void updateImgCount(Integer id ,int cnt) {mapCount.put(id,mapCount.get(id)+cnt);}

    /*public String checkingLiveness () {
        if(dead == true)
            return "Final";
        return "Current";
    }*/
}
