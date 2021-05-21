package com.ex03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase {
    //private int imgCount = 0;
    //private HashMap<Integer, Integer> mapCount = new HashMap<>();
    public List<WebCrawlerWithDepth> wc = new ArrayList<>();
    public DataBase() {}
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
