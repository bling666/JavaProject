package com.example.backend.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.example.backend.Entity.UserVocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Processor{
    private static int forgettingCurve[] = { //一共8个背诵阶段
            0,
            5,			// 5分钟
            30,			// 30分钟
            12*60,		// 12小时
            1*24*60,	// 1天
            2*24*60,	// 2天
            4*24*60,	// 4天
            7*24*60,	// 7天
            15*24*60	// 15天
    };
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

    // 根据记忆曲线，计算单词的deltstate
    public static int DeltState(UserVocabulary record) {
        long currentTime = System.currentTimeMillis();
        int timeDiff = (int)((currentTime - record.getLast_update().getTime()) / (1000 * 60)); //单位min

        if (timeDiff > forgettingCurve[record.getStage()]) {//如果超过了已知阶段的对应时长，则安排背诵
            for(int i = 0;i < 9;i++) {
                if(forgettingCurve[i] >= timeDiff)
                    return i - record.getStage();
            }
        }
        return 0;//else
    }
    //计算重要度
    public static double cal_Importance(UserVocabulary record) {
		double alpha = 1.0, beta = 1.0, gamma = 0.5, delta = 0.5;
		return alpha * record.getFrequency() + beta * DeltState(record) + gamma * record.getErrors() + delta * (record.getWord().length() - 4.5) * (record.getWord().length() - 4.5);
    }
}
