package com.example.backend.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Processor{
    public static String[] ReadAndToken(String raw) throws IOException{
        //读取停用词表
        List<String> stopwords =  Files.readAllLines(Paths.get("D:/stopwords.txt"));
        //读取文件
        List<String> tokenlist = new ArrayList<String>();
        TreeMap<String, Integer> countMap = new TreeMap<>();//用于去重和计数
            String temp = raw;
            String[] templist = temp.split("[^A-Za-z]");//这里只保留了字母
            for(String word : templist){
                if(word != null && word.length() > 0 && !stopwords.contains(word.toLowerCase())){//除去停用词和空String
                    word = word.toLowerCase();//把单词变成小写
                    if (countMap.get(word) == null) {
                        countMap.put(word, 1);
                    } else {
                        int num = countMap.get(word);
                        countMap.put(word, num + 1);//关键词存在，个数+1
                    }
                }
            }
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(countMap.entrySet());

        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //降序排序
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(Entry<String, Integer> t : list){{
            tokenlist.add(t.getKey());
        }}
        String[] token = (String[])tokenlist.toArray(new String[tokenlist.size()]);
        return token;
    }
}