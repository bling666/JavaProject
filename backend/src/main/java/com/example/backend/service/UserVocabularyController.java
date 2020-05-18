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
                     user_vocabulary_repo_api.save(st);
                 }
                 else
                 {
                     //TODO
                     //we need to use the crawler to check if the word doesn't right
                     //now we just delete the word
                     result.remove(single_result);
                 }
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
        user_vocabulary_repo_api.saveAll(re);
        return resultUtil.success(re);
    }
    //*****************two new APIs*******************************
    @RequestMapping("getonetask")
    @ResponseBody
    public baseResult<List<UserVocabulary>> get_one_task(Integer num)
    {
        List<UserVocabulary> re = user_vocabulary_repo_api.findAllByUid(1);
        TreeMap<UserVocabulary, Double> tempMap = new TreeMap<>(); 
        for(UserVocabulary t : re) {
            if(t.getStage() < 9)
        	    tempMap.put(t, Processor.cal_Importance(t));
        }
        if(tempMap.size() == 0){
            return resultUtil.error(500,"There are no words in the list");
        }
        List<Entry<UserVocabulary, Double>> list = new ArrayList<Entry<UserVocabulary, Double>>(tempMap.entrySet());
        String Msg = "success";
        if(num > list.size())
        {
            Msg = "there ara only "+list.size()+" words in the list";
        }
        if(num < list.size())
        {
            Collections.sort(list,new Comparator<Map.Entry<UserVocabulary,Double>>() {
                //降序排序
                public int compare(Entry<UserVocabulary, Double> o1, Entry<UserVocabulary, Double> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
        }
        //这里排好序之后，取前num个单词进行背诵
        re.clear();
        num = num < list.size()?num : list.size();
        for(int i = 0;i < num;i++){
            re.add(list.get(i).getKey());
        }
        return resultUtil.success(re, Msg);
    }

    @RequestMapping(value = "recite", method = RequestMethod.POST)
    public  baseResult<String> recite(@RequestParam("operator") Integer operator,@RequestParam("word") String word)
    {
        // op = 1 success, op = 0 failure
        try{
            UserVocabulary temp = user_vocabulary_repo_api.findByWord(word);
            if(temp==null)
                return resultUtil.error(500,"no such word in plan");
            user_vocabulary_repo_api.updateByWord(operator , word);
        }
        catch(Exception e){
            e.printStackTrace();
            return resultUtil.error(500,"error in reciting word");
        }
        return resultUtil.success("success");
    }
    //*******************************************************************************
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
