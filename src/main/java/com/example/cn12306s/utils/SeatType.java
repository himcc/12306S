package com.example.cn12306s.utils;

import java.util.HashMap;

public class SeatType {
    //商务座、一等座、二等座、硬卧、软卧、硬座、无座
    private static HashMap<String,Integer> nameToId = new HashMap<String,Integer>(){{
        put("商务座",1);
        put("一等座",2);
        put("二等座",3);
        put("硬卧",4);
        put("软卧",5);
        put("硬座",6);
        put("无座",7);
    }};

    private static HashMap<Integer,String> idToName = new HashMap<Integer,String>(){{
        put(1,"商务座");
        put(2,"一等座");
        put(3,"二等座");
        put(4,"硬卧");
        put(5,"软卧");
        put(6,"硬座");
        put(7,"无座");
    }};

    public static String getName(int id){
        return idToName.get(id);
    }
    public static int getId(String name){
        return nameToId.get(name);
    }
}
