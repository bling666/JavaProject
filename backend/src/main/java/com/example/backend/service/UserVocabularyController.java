package com.example.backend.service;

import com.example.backend.Dao.StopWordsRepo;
import com.example.backend.Dao.UserVocabularyRepo;
import com.example.backend.Dao.VocabularyRepo;
import com.example.backend.Entity.UserVocabulary;
import com.example.backend.Entity.Vocabulary;
import com.example.backend.core.Processor;
import com.example.backend.utility.baseResult;
import com.example.backend.utility.resultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map.Entry;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserVocabularyController {
    @Autowired
    private VocabularyRepo vocabularyrepo;
    @Autowired
    private UserVocabularyRepo user_vocabulary_repo_api;
    @Autowired
    private StopWordsRepo stopWordsRepo;
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public baseResult<List<String>> upload_text(HttpServletRequest request, @RequestParam("text") String  text)
    {
        System.out.println(text);
        try {
            HttpSession session = request.getSession();
            Integer uid = Integer.parseInt(session.getAttribute("uid").toString());
             List<String> result = Arrays.asList(Processor.ReadAndToken(stopWordsRepo,text));
             List<String> return_value = new ArrayList<>();
             for(int i = 0;i<result.size();i++)
             {
                 String single_result = result.get(i);
                 Vocabulary temp = vocabularyrepo.findOneByWord(single_result);
                 if(temp !=null)
                 {
                     UserVocabulary userVocabulary = user_vocabulary_repo_api.findByUidAndWord(uid,single_result);
                     if(user_vocabulary_repo_api.findByUidAndWord(uid,single_result)==null) {
                         UserVocabulary st = new UserVocabulary();
                         st.setUid(Integer.parseInt(session.getAttribute("uid").toString()));
                         st.setWord(temp.getWord());
                         st.setWid(temp.getId());
                         st.setJoined_time(new Date());
                         st.setLast_update(new Date());
                         user_vocabulary_repo_api.save(st);
                     }
                     else
                         System.out.printf("the word %s has exist",single_result);
                     return_value.add(single_result);
                 }
                 else
                 {
                     //TODO
                     //we need to use the crawler to check if the word doesn't right
                     //now we just delete the word
                 }
             }
            return resultUtil.success(return_value);
        } catch (IOException e) {
            e.printStackTrace();
            return resultUtil.error(500,"can't find the stop_word.txt");
        }

    }

    @RequestMapping("list")
    public baseResult<List<UserVocabulary>> list_word(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Integer uid = Integer.parseInt(session.getAttribute("uid").toString());
        List<UserVocabulary> re = user_vocabulary_repo_api.findAllByUid(uid);
        //TODO select the words in the plan to return and modify related params
        user_vocabulary_repo_api.saveAll(re);
        return resultUtil.success(re);
    }
    //*****************two new APIs*******************************
    @RequestMapping("getonetask")
    @ResponseBody
    public baseResult<List<Vocabulary>> get_one_task(HttpServletRequest request,Integer num)
    {
        HttpSession session = request.getSession();
        Integer uid = Integer.parseInt(session.getAttribute("uid").toString());
        List<UserVocabulary> re = user_vocabulary_repo_api.findAllByUid(uid);
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

        List<Vocabulary> response_data = new ArrayList<>();
        for(int i = 0;i<re.size();i++)
        {
            Vocabulary single_result = vocabularyrepo.findOneByWord(re.get(i).getWord());
            response_data.add(single_result);
        }

        return resultUtil.success(response_data, Msg);
    }

    @RequestMapping(value = "recite", method = RequestMethod.POST)
    public  baseResult<String> recite(HttpServletRequest request,@RequestParam("operator") Integer operator,@RequestParam("word") String word)
    {
        HttpSession session = request.getSession();
        Integer uid = Integer.parseInt(session.getAttribute("uid").toString());
        // op = 1 success, op = 0 failure
        try {
            UserVocabulary temp = user_vocabulary_repo_api.findByUidAndWord(uid,word);
            if (temp == null)
                return resultUtil.error(500, "no such word in plan");
            UserVocabulary tem = user_vocabulary_repo_api.findByUidAndWord(uid,word);
            if (operator == 0) {
                tem.setErrors(tem.getErrors() + 1);
                tem.setStage(tem.getStage() > 0 ? tem.getStage() - 1 : 0);
            } else if (operator == 1) {//success
                tem.setStage(tem.getStage() + 1);
            } else {

            }
            tem.setLast_update(new Date());
            tem.setRecite_times(tem.getRecite_times() + 1);
            user_vocabulary_repo_api.save(tem);
        }
        catch(Exception e){
            e.printStackTrace();
            return resultUtil.error(500,"error in reciting word");
        }
        return resultUtil.success("success");
    }
    //*******************************************************************************
    @RequestMapping(value = "delete_word_in_plan",method = RequestMethod.DELETE)
    public baseResult<String> delete_word(HttpServletRequest request,@RequestParam("word") String word)
    {
        HttpSession session = request.getSession();
        Integer uid = Integer.parseInt(session.getAttribute("uid").toString());
        System.out.println(word);
        try {

            UserVocabulary temp = user_vocabulary_repo_api.findByUidAndWord(uid,word);
            if(temp==null)
                return resultUtil.error(500,"no such word in plan");
            user_vocabulary_repo_api.deleteByIdAndWord(uid,word);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return resultUtil.error(500,"error in deleting word");
        }
        return resultUtil.success("success");
    }
}
