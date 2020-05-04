package com.example.backend.service;

import com.example.backend.Entity.vocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class vocabularyController {
    @Autowired
    private vocabularyRepo vocabularyrepo;

    @RequestMapping("getoneword")
    @ResponseBody
    public List<vocabulary> get_one_word(String keyword,Integer top)
    {
        System.out.println(keyword);
        System.out.println(top);
        if(top==null)
        {
            top = 1;
        }
        return vocabularyrepo.findAllByWordLike(keyword,top);
    }
}
