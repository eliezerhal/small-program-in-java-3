package com.ex03;

import java.util.HashMap;

public class DataBase {
    //private int imgCount = 0;
    private HashMap<Integer, Integer> mapCount = new HashMap<>();
    public DataBase() {}
    public int getImgCount(Integer id) {return mapCount.get(id);}
    public void setImgCount(Integer id) {mapCount.put(id, 0);}
    public void updateImgCount(Integer id ,int cnt) {mapCount.put(id,mapCount.get(id)+cnt);}
}
