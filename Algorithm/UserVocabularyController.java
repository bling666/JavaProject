package com.example.backend.service;

import com.example.backend.Dao.UserVocabularyRepo;
import com.example.backend.Dao.VocabularyRepo;
import com.example.backend.Entity.UserVocabulary;
import com.example.backend.Entity.Vocabulary;
import com.example.backend.core.Processor;
import com.example.backend.utility.baseResult;
import com.example.backend.utility.resultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import java.util.Map.Entry;
@RestController
@RequestMapping("/api")
public class UserVocabularyController {
    @Autowired
    private VocabularyRepo vocabularyrepo;
    @Autowired
    private UserVocabularyRepo user_vocabulary_repo_api;

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public baseResult<List<String>> upload_text(@RequestParam("text") String  text)
    {

        System.out.println(text);
        try {
        	List<String> result = Arrays.asList(Processor.ReadAndToken(text));
        	
             //*****将单词按频率分为5级
             int step = result.size() / 5;
             int cnt = step;
             int level = 5;
             //**********
             
             for(int i = 0;i<result.size();i++)	
             {
                 String single_result = result.get(i);
                 Vocabulary temp = vocabularyrepo.findOneByWord(single_result);
                 if(temp !=null)
                 {
                     UserVocabulary st = new UserVocabulary();
                     st.setUid(1);
                     st.setWord(temp.getWord());
                     st.setWid(temp.getId());
                     st.setJoined_time(new Date());
                     st.setLast_update(new Date());
                     
                     //*******
                     st.setFrequency(level);
                     //********
                     
                     user_vocabulary_repo_api.save(st);
                 }
                 else
                 {
                     //TODO
                     //we need to use the crawler to check if the word doesn't right
                     //now we just delete the word
                     result.remove(single_result);
                 }
                 
                 //********
                 cnt -= 1;
                 if(cnt == 0) {
                	 cnt = step;
                	 level -= 1;
                 }
                 //*********
                 
             }
            return resultUtil.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            return resultUtil.error(500,"can't find the stop_word.txt");
        }

    }

    @RequestMapping("list")
    public baseResult<List<UserVocabulary>> list_word()
    {
        List<UserVocabulary> re = user_vocabulary_repo_api.findAllByUid(1);
        //TODO select the words in the plan to return and modify related params
        TreeMap<String, Double> tempMap = new TreeMap<>();
        for(UserVocabulary t : re) {
        	tempMap.put(t.getWord(), Processor.cal_Importance(t));
        }
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(tempMap.entrySet());

        Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
            //降序排序
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        //这里排好序之后，取前k个单词进行背诵
        user_vocabulary_repo_api.saveAll(re);
        return resultUtil.success(re);
    }

    @RequestMapping(value = "delete_word_in_plan",method = RequestMethod.DELETE)
    public baseResult<String> delete_word(@RequestParam("word") String word)
    {
        System.out.println(word);
        try {
            UserVocabulary temp = user_vocabulary_repo_api.findByWord(word);
            if(temp==null)
                return resultUtil.error(500,"no such word in plan");
            user_vocabulary_repo_api.deleteByWord(word);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return resultUtil.error(500,"error in deleting word");
        }
        return resultUtil.success("success");
    }
}
